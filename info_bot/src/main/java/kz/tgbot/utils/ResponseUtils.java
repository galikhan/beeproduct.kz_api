package kz.tgbot.utils;


import io.vertx.core.AsyncResult;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ResponseUtils {

    private static Logger log = LogManager.getLogger(ResponseUtils.class);

    public static <T> void ok(T t, RoutingContext ctx) {
        ctx.response().end(Json.encode(t));
    }

    public static <T> void error500(Throwable t, RoutingContext ctx) {
        ctx.response().setStatusCode(500).setStatusMessage(t.getMessage()).end();
    }

    public static <T> void error(int code, String message, RoutingContext ctx) {
        ctx.response().setStatusCode(code).setStatusMessage(message).end();
    }

    public static <T> void processResult(AsyncResult<T> t, RoutingContext ctx) {
        if(t.succeeded()) {
            ok(t.result(), ctx);
        } else {
            ctx.fail(500, t.cause());
        }
    }
}
