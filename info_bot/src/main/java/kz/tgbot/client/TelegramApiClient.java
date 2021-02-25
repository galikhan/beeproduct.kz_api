package kz.tgbot.client;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelegramApiClient {

    private static String apiUrl = "https://api.telegram.org";
    private static String token = "bot1698913294:AAHKHSXI7ecR4D2V14GZq7Y1k_CBExsv2JA";
    private Logger log = LogManager.getLogger(TelegramApiClient.class);
    private WebClient client;
    public String url = apiUrl + "/" + token;

    public TelegramApiClient(Vertx vertx) {
        client = WebClient.create(vertx);
    }

    public void sendMessage() {
        log.info("sendmessage");
//        String url = apiUrl + "/" + token + "/getMe";
//        log.info("address url {}", url );
        client.getAbs(url + "/getMe")
//                .get(apiUrl, "/" + token + "/getMe")
//                .get(443, apiUrl, "")
//                .get(apiUrl)
//                .ssl(true)
                .send()
                .onSuccess(response -> {
                    log.info("Received response with status code {}" + response.statusCode());
                    log.info("result  {}", response.bodyAsString());

                })
                .onFailure(response -> {
                    response.printStackTrace();
                });

    }

    public void register() {

//        log.info("register {}", url + "/account.registerDevice");
//
//        client
//                .get(url + "/account.registerDevice")
//                .send(
//
//                        result -> {
//                            log.info("waiting for response");
//
//                            if (result.succeeded()) {
//                                HttpResponse<Buffer> response = result.result();
//                                log.info("Received response with status code " + response.statusCode());
//                                log.info("response value {}", response.bodyAsString());
//                            } else {
//                                log.error("error", result.cause());
//                            }
//                        });

    }

    public Future<HttpResponse<Buffer>> getUpdates(Integer chatUpdateId) {
        return client.getAbs(url + "/getUpdates?offset=" + chatUpdateId).send();
    }
}
