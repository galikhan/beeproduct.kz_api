package kz.beeproduct;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthenticationHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import kz.beeproduct.helper.JWTAuthHelper;
import kz.beeproduct.routers.BeeProductRouter;
import kz.beeproduct.utils.DbUtils;
import kz.beeproduct.verticles.MainVerticle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AppVert extends AbstractVerticle {

    static {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        System.setProperty("vertx-config-path", "conf/config.json");

        DatabindCodec.mapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DatabindCodec.mapper().registerModule(new Jdk8Module());
        DatabindCodec.mapper().registerModule(new JavaTimeModule());
        DatabindCodec.mapper().configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        DatabindCodec.mapper().configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        DatabindCodec.mapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        DatabindCodec.mapper().setDateFormat(df);

    }


    @Override
    public void start() throws Exception {
        super.start();

        ConfigRetriever configRetriever = ConfigRetriever.create(vertx);
        configRetriever.getConfig(result -> {

            JsonObject config = result.result();
            DbUtils.init(config.getString("db.port"), config.getString("db.name"));

            vertx.deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setConfig(config));
        });
    }

}

