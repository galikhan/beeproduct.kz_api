package kz.tgbot.routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APIRouter {

    private Router router;
    private Logger log = LogManager.getLogger(APIRouter.class);

    public APIRouter(Router router, Vertx vertx) {

//        SendReceive1CMessageHandler handler = new SendReceive1CMessageHandler(vertx);

        this.router = router;
        Route route = this.router.post("/approval");

//        route.handler(handler::approvalMessage);
        route.failureHandler(error -> {
            log.info("error {}", error);
        });

//        this.router.post("/send").handler(handler::sendMessage);
    }

    public Router getRouter() {
        return this.router;
    }
}
