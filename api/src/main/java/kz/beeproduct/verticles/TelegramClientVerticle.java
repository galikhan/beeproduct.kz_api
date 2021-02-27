package kz.beeproduct.verticles;

import io.vertx.core.AbstractVerticle;
import kz.beeproduct.client.TelegramApiClient;
import kz.beeproduct.dao.TgUserDao;
import kz.beeproduct.dao.impl.TgUserDaoImpl;
import kz.beeproduct.utils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelegramClientVerticle extends AbstractVerticle {

    private Logger log = LogManager.getLogger(TelegramClientVerticle.class);
    private TelegramApiClient client;

    @Override
    public void start() throws Exception {
        super.start();

        client = new TelegramApiClient(vertx);
        vertx.eventBus().consumer("telegram.message").handler(message -> {
            log.info("hello log info {}", message.body());
            String messageBody = (String) message.body();
            DbUtils.blocking(ctx -> {
                TgUserDao tgUserDao = new TgUserDaoImpl(ctx);
                tgUserDao.findByRole("admin").stream().forEach(user -> {
                    client.sendMessage(user.chatId, messageBody);
                });
                return null;
            }, result -> {}, vertx);
        });
    }
}
