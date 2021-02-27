package kz.tgbot;


import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import kz.tgbot.utils.DbUtils;
import kz.tgbot.utils.HttpUtils;
import kz.tgbot.verticles.APIVerticle;
import kz.tgbot.verticles.TelegramClientVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppVerticle extends AbstractVerticle {

    private static Logger log = LogManager.getLogger(AppVerticle.class);

    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        System.setProperty("vertx-config-path", "conf/config.json");
    }

    @Override
    public void start() throws Exception {
        super.start();
        log.info("Welcome to - {}", "APP_VERTICLE");
        ConfigRetriever configRetriever = ConfigRetriever.create(vertx);
        configRetriever.getConfig(result -> {

            JsonObject config = result.result();

            init();
            DbUtils.init(config.getInteger("db.port"), config.getString("db.name"));
            vertx.deployVerticle(APIVerticle.class.getName());
            vertx.deployVerticle(TelegramClientVerticle.class.getName());

        });

    }

    public void init() {
        HttpUtils.init();
    }
}

