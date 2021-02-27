package kz.tgbot.client;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import kz.tgbot.dto.MessageDto;
import kz.tgbot.dto.UpdateDto;
import kz.tgbot.dto.telegram.send.SendMessageDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

public class TelegramApiClient {

    private static String apiUrl = "https://api.telegram.org";
    private static String token = "bot1698913294:AAHKHSXI7ecR4D2V14GZq7Y1k_CBExsv2JA";
    private Logger log = LogManager.getLogger(TelegramApiClient.class);
    private WebClient client;
    public String url = apiUrl + "/" + token;

    public TelegramApiClient(Vertx vertx) {
        client = WebClient.create(vertx);
    }

    public void sendMessage(Integer chatId, String text) {
        log.info("send message");

        SendMessageDto sendMessage = new SendMessageDto();
        sendMessage.text = text;
        sendMessage.chat_id = chatId;


        client.postAbs(url + "/sendMessage")
                .sendJson(sendMessage)
                .onComplete(result -> {

                    if(result.succeeded()) {
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
