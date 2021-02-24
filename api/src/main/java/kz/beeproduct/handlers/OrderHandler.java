package kz.beeproduct.handlers;

import io.vertx.core.http.Cookie;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import kz.beeproduct.dao.OrdersDao;
import kz.beeproduct.dao.ProductDao;
import kz.beeproduct.dao.UsersDao;
import kz.beeproduct.dao.impl.OrdersDaoImpl;
import kz.beeproduct.dao.impl.ProductDaoImpl;
import kz.beeproduct.dao.impl.UsersDaoImpl;
import kz.beeproduct.dto.OrdersDto;
import kz.beeproduct.dto.ProductDto;
import kz.beeproduct.dto.UsersDto;
import kz.beeproduct.utils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

import static kz.beeproduct.utils.ResponseUtils.ok;
import static kz.beeproduct.utils.ResponseUtils.processResult;

public class OrderHandler {

    public static final String COOKIE_NAME = "vertx-web.session";
    private Logger log = LogManager.getLogger(OrderHandler.class);

    public void cookie(RoutingContext routingContext) {
        log.info("init cookie");
        ok("hello", routingContext);
    }


    public void addToCart(RoutingContext routingContext) {

        log.info("add to cart");

        JsonObject body = routingContext.getBodyAsJson();
        DbUtils.blocking(ctx -> {

            Cookie cookie = routingContext.getCookie(COOKIE_NAME);
            String sessionId = cookie.getValue();

            log.info("sess id {} ", sessionId);
            UsersDao usersDao = new UsersDaoImpl(ctx);
            UsersDto user = usersDao.findBySession(sessionId);
            if (user == null) {
                log.info("users is not found session id - {}", sessionId);
                user = new UsersDto();
                user.session = sessionId;
                user.login = sessionId;
                usersDao.create(user);
            }

            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            OrdersDto orders = ordersDao.findByUser(sessionId);

            if (orders == null) {
                orders = new OrdersDto();
                orders.orderTime = LocalDateTime.now();
                orders.user = user.login;
                orders = ordersDao.create(orders);
            }

            Long productId = body.getLong("productId");
            log.info("body - {}", productId);
            ordersDao.addProduct(orders.id, productId);

            return orders;

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }

    public void removeFromCart(RoutingContext routingContext) {

        Long productId = Long.parseLong(routingContext.pathParam("product"));
        log.info("product {}", productId);

        DbUtils.blocking(ctx -> {

            Cookie cookie = routingContext.getCookie(COOKIE_NAME);
            String sessionId = cookie.getValue();
            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            OrdersDto order = ordersDao.findByUser(sessionId);
            return ordersDao.removeProduct(order.id, productId);

        }, result -> processResult(result, routingContext), routingContext.vertx());
    }

    public void findOrderBySesion(RoutingContext routingContext) {

        log.info(" findOrderBySesion cookie count {}", routingContext.cookieCount());

        DbUtils.blocking(ctx -> {

            Cookie cookie = routingContext.getCookie(COOKIE_NAME);
            String sessionId = cookie.getValue();

            log.info("session {}", sessionId);

            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            OrdersDto order = ordersDao.findByUser(sessionId);
            log.info("order {}", order);

            ProductDao productDao = new ProductDaoImpl(ctx);
            List<ProductDto> products = productDao.findByOrder(order.id);
//            log.info("prods {}", (products != null ? products.size() : 0));
            products.stream().forEach(v -> {
                System.out.println(v);
            });
            order.products = products;

            return order;

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }

    public void updateProductAmount(RoutingContext routingContext) {

        log.info("updateProductAmount");
        JsonObject body = routingContext.getBodyAsJson();
        Integer amount = body.getInteger("amount");
        Long productId = Long.parseLong(routingContext.pathParam("product"));

        DbUtils.blocking(ctx -> {

            Cookie cookie = routingContext.getCookie(COOKIE_NAME);
            String sessionId = cookie.getValue();
            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            OrdersDto orders = ordersDao.findByUser(sessionId);

            return ordersDao.updateProduct(orders.id, productId, amount);

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }

    public void updateOrder(RoutingContext routingContext) {

        log.info("update order");
        String status = routingContext.pathParam("status");

        UsersDto user = Json.decodeValue(routingContext.getBodyAsString(), UsersDto.class);
        DbUtils.blocking(ctx -> {

            Cookie cookie = routingContext.getCookie(COOKIE_NAME);
            String sessionId = cookie.getValue();

            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            OrdersDto order = ordersDao.findByUser(sessionId);
            log.info("order {}", order);
            order.status = status;
            ordersDao.update(order);

            UsersDao usersDao = new UsersDaoImpl(ctx);
            user.login = sessionId;
            user.session = sessionId;

            usersDao.save(user);

            return user;

        }, result -> processResult(result, routingContext), routingContext.vertx());
    }

    public void getAll(RoutingContext routingContext) {
        log.info("getAll");
        Integer page = Integer.valueOf(routingContext.queryParams().get("page"));

        DbUtils.blocking(ctx -> {

            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            UsersDao usersDao = new UsersDaoImpl(ctx);
            ProductDao productDao = new ProductDaoImpl(ctx);
            List<OrdersDto> orders = ordersDao.getAll(page);
            orders.stream().forEach(r -> {
                r.user = UsersDto.getInfo(usersDao.findBySession(r.user));
                r.products = productDao.findByOrder(r.id);
            });

            return orders;

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }

    public void getCount(RoutingContext routingContext) {
        log.info("getCount");

        DbUtils.blocking(ctx -> {

            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            return ordersDao.getCount();

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }
}
