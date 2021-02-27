package kz.beeproduct.client;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelegramApiClient {

    private static String apiUrl = "https://api.telegram.org";
    private static String token = "bot1698913294:AAHKHSXI7ecR4D2V14GZq7Y1k_CBExsv2JA";
    public String url = apiUrl + "/" + token;
    private Logger log = LogManager.getLogger(TelegramApiClient.class);
    private WebClient client;

    public TelegramApiClient(Vertx vertx) {
        client = WebClient.create(vertx);
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
