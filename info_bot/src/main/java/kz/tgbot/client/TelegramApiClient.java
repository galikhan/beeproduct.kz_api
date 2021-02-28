package kz.tgbot.client;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import kz.tgbot.dto.telegram.send.SendMessageDto;
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

        SendMessageDto sendMessage = new SendMessageDto();
        sendMessage.text = text;
        sendMessage.chat_id = chatId;


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


    public Future<HttpResponse<Buffer>> getUpdates(Integer chatUpdateId) {
        return client.getAbs(url + "/getUpdates?offset=" + chatUpdateId).send();
    }
}
