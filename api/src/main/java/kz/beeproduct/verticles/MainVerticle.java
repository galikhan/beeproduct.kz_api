package kz.beeproduct.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import kz.beeproduct.routers.BeeProductRouter;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        CorsHandler corsHandler = CorsHandler.create("*");
        addCors(corsHandler);
        router.route().handler(corsHandler);
        router.route().handler(BodyHandler.create());

        BeeProductRouter beeProductRouter = new BeeProductRouter(router, vertx, config());
        router = beeProductRouter.getRouter();

        vertx.createHttpServer().requestHandler(router).listen(config().getInteger("http.port"));
    }


    public CorsHandler addCors(CorsHandler corsHandler) {
        corsHandler
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.PATCH)
                .allowedHeader("Authorization")
                .allowedHeader("user-agent")
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type")
                .allowedHeader("Content-Length")
                .allowedHeader("X-Requested-With")
                .allowedHeader("x-auth-token")
                .allowedHeader("Location")
                .exposedHeader("Location")
                .exposedHeader("Content-Type")
                .exposedHeader("Content-Length")
                .exposedHeader("ETag");
        return corsHandler;
    }}
