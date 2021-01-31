package kz.beeproduct.handlers;

import io.vertx.core.http.Cookie;
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

        System.out.println("add to cart");
        JsonObject body = routingContext.getBodyAsJson();
        System.out.println(body.toString());
        System.out.println("cookie count: " + routingContext.cookieCount());
        routingContext.cookieMap().entrySet().stream().forEach(v -> {
            System.out.println("k : " + v.getKey());
            System.out.println("v : " + v.getValue().getValue());
        });


        DbUtils.blocking(ctx -> {

            Cookie cookie = routingContext.getCookie(COOKIE_NAME);
            String sessionId = cookie.getValue();

            System.out.println("sess id : " + sessionId);
            UsersDao usersDao = new UsersDaoImpl(ctx);
            UsersDto user = usersDao.findBySession(sessionId);
            if (user == null) {
                System.out.println("users is not found session id - " + sessionId);
                user = new UsersDto();
                user.session = sessionId;
                user.login = sessionId;
                usersDao.create(user);
            }

            OrdersDao ordersDao = new OrdersDaoImpl(ctx);
            OrdersDto orders = ordersDao.findByUser(sessionId);

            if (orders == null) {
                orders = new OrdersDto();
                orders.amount = 1;
                orders.orderTime = LocalDateTime.now();
                orders.user = user.login;
                orders = ordersDao.save(orders);
            }

            Long productId = body.getLong("productId");
            System.out.println("body : " + productId);
            ordersDao.addProduct(orders.id, productId);


//            throw new RuntimeException("easdafafdf");
            return orders;

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }

    public void removeFromCart(RoutingContext routingContext) {

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
            log.info("prods {}", (products != null ? products.size() : 0));
            order.products = products;

            return order;

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }
}
