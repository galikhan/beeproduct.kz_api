package kz.beeproduct.routers;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.AuthenticationHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.SessionStore;
import kz.beeproduct.handlers.OrderHandler;
import kz.beeproduct.handlers.PrivateProductHandler;
import kz.beeproduct.handlers.ProductHandler;
import kz.beeproduct.helper.JWTAuthHelper;
import kz.beeproduct.utils.ResponseUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeeProductRouter {

    Router router;
    private Logger log = LogManager.getLogger(BeeProductRouter.class);

    public BeeProductRouter(Router router, Vertx vertx) {

        SessionStore sessionStore = SessionStore.create(vertx);
        SessionHandler sessionHandler = SessionHandler.create(sessionStore);
//        sessionHandler.setCookieSecureFlag(false);
        router.route().handler(sessionHandler);

        //private routes
        JWTAuthHelper helper = new JWTAuthHelper(vertx);
        AuthenticationHandler authenticationHandler = JWTAuthHandler.create(helper.getProvider());
        router.route("/api/private/*").handler(authenticationHandler);

        OrderHandler orderHandler = new OrderHandler();
        PrivateProductHandler privateProductHandler = new PrivateProductHandler();

        router.post("/api/private/product").handler(privateProductHandler::create);
        router.put("/api/private/product").handler(privateProductHandler::update);
        router.delete("/api/private/product").handler(privateProductHandler::delete);
        router.get("/api/private/product/:productId").handler(privateProductHandler::findById);
        router.get("/api/private/order/all").handler(orderHandler::getAll);
        router.get("/api/private/order/count").handler(orderHandler::getCount);
        //private routes

        router.post("/api/authenticate").handler(data-> {
            JsonObject jsonObject = data.getBodyAsJson();
            String login = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            if("admin".equals(login) && "admin".equals(password)) {
                JsonObject result = new JsonObject();
                result.put("token", helper.getToken());
                data.response().end(result.toString());
            }
        });

        router.get("/api").handler(result -> {
            result.response().end(Json.encode("hello:hello"));
        });

        router.get("/api/cookie").handler(orderHandler::cookie);

        router.get("/api/order").handler(orderHandler::findOrderBySesion);
        router.post("/api/order/product").handler(orderHandler::addToCart);
        router.put("/api/order/product/:product").handler(orderHandler::updateProductAmount);
        router.delete("/api/order/product/:product").handler(orderHandler::removeFromCart);

        router.post("/api/order/:status").handler(orderHandler::updateOrder);

        ProductHandler productHandler = new ProductHandler();
        router.get("/api/product/all").handler(productHandler::findAll);

        router.errorHandler(500, error -> {
            if (error.failed()) {
                error.failure().printStackTrace();
                ResponseUtils.error(500,  "failure", error);
            }
        });

        this.router = router;
    }

    public Router getRouter() {
        return router;
    }
}
