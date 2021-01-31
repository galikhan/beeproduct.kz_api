package kz.beeproduct.handlers;

import io.vertx.ext.web.RoutingContext;
import kz.beeproduct.dao.ProductDao;
import kz.beeproduct.dao.impl.ProductDaoImpl;
import kz.beeproduct.utils.DbUtils;
import kz.beeproduct.utils.ResponseUtils;

public class ProductHandler {


    public void findAll(RoutingContext routingContext) {
        DbUtils.blocking(ctx -> {

            ProductDao productDao = new ProductDaoImpl(ctx);
            return productDao.findAll();

        }, result -> ResponseUtils.processResult(result, routingContext), routingContext.vertx());

    }
}
