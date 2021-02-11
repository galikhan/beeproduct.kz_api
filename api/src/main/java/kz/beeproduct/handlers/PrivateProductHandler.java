package kz.beeproduct.handlers;

import io.vertx.core.http.Cookie;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import kz.beeproduct.dao.OrdersDao;
import kz.beeproduct.dao.ProductDao;
import kz.beeproduct.dao.impl.OrdersDaoImpl;
import kz.beeproduct.dao.impl.ProductDaoImpl;
import kz.beeproduct.dto.OrdersDto;
import kz.beeproduct.dto.ProductDto;
import kz.beeproduct.utils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static kz.beeproduct.utils.ResponseUtils.processResult;

public class PrivateProductHandler {

    private Logger log = LogManager.getLogger(PrivateProductHandler.class);

    public void create(RoutingContext routingContext) {

        ProductDto product = Json.decodeValue(routingContext.getBodyAsString(), ProductDto.class);
        log.info("product {}", product);

        DbUtils.blocking(ctx -> {

            ProductDao productDao = new ProductDaoImpl(ctx);
            return productDao.create(product);

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }

    public void update(RoutingContext routingContext) {
        ProductDto product = Json.decodeValue(routingContext.getBodyAsString(), ProductDto.class);
        log.info("product {}", product);

        DbUtils.blocking(ctx -> {

            ProductDao productDao = new ProductDaoImpl(ctx);
            return productDao.update(product);

        }, result -> processResult(result, routingContext), routingContext.vertx());
    }

    public void delete(RoutingContext routingContext) {

    }

    public void findById(RoutingContext routingContext) {

        Long productId = Long.parseLong(routingContext.pathParam("productId"));
        log.info("productId {}", productId);

        DbUtils.blocking(ctx -> {

            ProductDao productDao = new ProductDaoImpl(ctx);
            return productDao.findById(productId);

        }, result -> processResult(result, routingContext), routingContext.vertx());
    }
}
