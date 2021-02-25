package kz.tgbot.utils;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtils {

    private static WebClient client;
    private static Logger log = LogManager.getLogger(HttpUtils.class);

    public HttpUtils() {
        init();
    }

    public static void init() {
        client = WebClient.create(Vertx.vertx());
    }

    public static WebClient getWebClient(){
        if(client == null) {
            log.warn("Web clien NOT INITITALISED");
        }
        return client;
    }
}
