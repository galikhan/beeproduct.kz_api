package kz.beeproduct.utils;


import io.vertx.core.AsyncResult;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class ResponseUtils {

    private static Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    public static <T> void ok(T t, RoutingContext ctx) {
        ctx.response().end(Json.encode(t));
    }

    public static <T> void error500(String message, RoutingContext ctx) {
        ctx.response().setStatusCode(500).setStatusMessage(message).end();
    }

    public static <T> void error(int code, String message, RoutingContext ctx) {
        ctx.response().setStatusCode(code).setStatusMessage(message).end();
    }

    public static <T> void processResult(AsyncResult<T> t, RoutingContext ctx) {
        if(t.succeeded()) {
            ok(t.result(), ctx);
        } else {
            log.error("error", t.cause());
            ctx.fail(500);
//            throw new RuntimeException("erroorrs");
        }
    }
}
