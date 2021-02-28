package kz.tgbot.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import kz.beeproduct.dao.TgUserDao;
import kz.beeproduct.dao.impl.TgUserDaoImpl;
import kz.beeproduct.dto.TgUserDto;
import kz.tgbot.client.TelegramApiClient;
import kz.tgbot.dto.ResultDto;
import kz.tgbot.dto.UpdateDto;
import kz.tgbot.utils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelegramClientVerticle extends AbstractVerticle {

    private TelegramApiClient telegramApiClient;
    private Logger log = LogManager.getLogger(TelegramApiClient.class);
    private Integer chatUpdateId = null;
    private Vertx vertxInstance;

    @Override
    public void start() throws Exception {
        super.start();
        this.vertxInstance = vertx;
        telegramApiClient = new TelegramApiClient(vertx, config());
        fetchUpdateOnce();
    }

    private void fetchUpdateOnce() {
        vertx.setTimer(1000, handler -> {
            fetchUpdate();
        });
    }

    private void fetchUpdate() {

        log.info("fetch start");
        telegramApiClient.getUpdates(chatUpdateId)
                .onSuccess(update -> {

                    UpdateDto message = Json.decodeValue(update.bodyAsString(), UpdateDto.class);
                    int size = message.result.size();
                    if (size > 0) {

                        chatUpdateId = message.result.get(size - 1).update_id;
                        increaseOffset();

                        message.result.stream().forEach(msg -> {
                            handleMessage(msg);
                        });
                    }

                    log.info("size of messages {}", size);
                    fetchUpdateOnce();

                }).onFailure(error -> {
            log.error("error", error);
            fetchUpdateOnce();
        });
    }

    private void increaseOffset() {
        chatUpdateId = chatUpdateId + 1;
    }

    private void handleMessage(ResultDto result) {

        if ("subscribe".equals(result.message.text)) {

            TgUserDto user = new TgUserDto();
            user.chatId = result.message.chat.id;
            user.username = result.message.from.username;
            user.role = "admin";
            user.firstname = result.message.from.first_name;

            DbUtils.blocking(ctx -> {
                TgUserDao tgUserDao = new TgUserDaoImpl(ctx);
                TgUserDto savedUser = tgUserDao.findByChatId(user.chatId);
                if (savedUser == null) {
                    savedUser = tgUserDao.create(user);
                }
                return savedUser;
            }, then -> {
                telegramApiClient.sendMessage(result.message.chat.id, "Вы подписались на уведомления");
            }, this.vertxInstance);

        }
    }
}
