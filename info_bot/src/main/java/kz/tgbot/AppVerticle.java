package kz.tgbot;


import io.vertx.core.AbstractVerticle;
import kz.tgbot.utils.HttpUtils;
import kz.tgbot.verticles.APIVerticle;
import kz.tgbot.verticles.TelegramClientVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppVerticle extends AbstractVerticle {

    private static Logger log = LogManager.getLogger(AppVerticle.class);

    @Override
    public void start() throws Exception {
        super.start();
        log.info("Welcome to - {}", "APP_VERTICLE");
        init();
        vertx.deployVerticle(APIVerticle.class.getName());
        vertx.deployVerticle(TelegramClientVerticle.class.getName());
    }

    public void init() {
        HttpUtils.init();
    }
}

