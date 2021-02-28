package kz.beeproduct.client;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelegramApiClient {


    private String apiUrl;
    private String token;
    private String url;
    private Logger log = LogManager.getLogger(TelegramApiClient.class);
    private WebClient client;

    public TelegramApiClient(Vertx vertx, JsonObject config) {
        client = WebClient.create(vertx);
        apiUrl = config.getString("telegram.url");
        token = config.getString("telegram.bot.token");
        url = apiUrl + "/" + token;
    }

    public void sendMessage(Integer chatId, String text) {
        log.info("send message");

        JsonObject sendMessage = new JsonObject();
        sendMessage.put("text", text);
        sendMessage.put("chat_id", chatId);


        client.postAbs(url + "/sendMessage")
                .sendJson(sendMessage)
                .onComplete(result -> {

                    if (result.succeeded()) {
                        log.info("ok {}", result.result());
                    } else {
                        log.error("error ", result.cause());
                    }
                }).onFailure(e -> {
            log.error("error", e);
        });


    }

}
