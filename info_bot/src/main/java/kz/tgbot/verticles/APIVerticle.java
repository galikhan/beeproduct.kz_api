package kz.tgbot.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import kz.tgbot.routers.APIRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APIVerticle extends AbstractVerticle {

    private Logger log = LogManager.getLogger(APIVerticle.class);

    @Override
    public void start() throws Exception {
        super.start();
        log.info("APIVerticle started");

        CorsHandler corsHandler = CorsHandler.create("*");
        addCors(corsHandler);

        Router router = Router.router(vertx);
        router.errorHandler(500, e -> {
           log.error("er", e);
        });
        router.route().handler(corsHandler);
        router.route().handler(BodyHandler.create());
        router.route("/").handler(ctx -> ctx.response().end("Hello World from Vert.x-Web!"));

        APIRouter apiRouter = new APIRouter(router, vertx);
        router = apiRouter.getRouter();

        vertx.createHttpServer()
                .requestHandler(router)
                .exceptionHandler(e -> {
                    log.info("error in http");
                    e.printStackTrace();
                })
                .listen(8080);
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
    }

}
