package kz.beeproduct.helper;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JWTAuthHelper {

    private static String ALGORITHM = "RS256";
    private static Integer EXPIRE_IN_SECONDS = 60 * 60 * 24;

    private Vertx vertx;
    private JWTAuthOptions jwtAuthOptions;
    private JWTAuth provider;

    private Logger log = LogManager.getLogger(JWTAuthHelper.class);

    public JWTAuthHelper(Vertx vertx) {
        this.vertx = vertx;
        initJWTAuthOptions();
        initJWTAuth();
    }

    private void initJWTAuth() {
        provider = JWTAuth.create(vertx, jwtAuthOptions);
    }

    private void initJWTAuthOptions() {
        jwtAuthOptions = new JWTAuthOptions();
        jwtAuthOptions.addPubSecKey(pubSecKeyOptions("public.pem"));
        jwtAuthOptions.addPubSecKey(pubSecKeyOptions("private_key.pem"));
    }

    private String getFileContent(String filename) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();

            while(line != null) {
                buffer.append(line);
                buffer.append("\n");
                line = reader.readLine();
            }
            return buffer.toString();
        } catch (Exception e) {
            log.error("error", e);
        }
        log.warn("file contents not red");
        return null;
    }

    private PubSecKeyOptions pubSecKeyOptions(String filename) {
        PubSecKeyOptions pubSecKeyOptions = new PubSecKeyOptions();
        pubSecKeyOptions.setAlgorithm(ALGORITHM);
        pubSecKeyOptions.setBuffer(getFileContent(filename));
        return pubSecKeyOptions;
    }

    public String getToken() {
        return getTokenWithJson(new JsonObject());
    }

    public String getTokenWithJson(JsonObject json) {
        JWTOptions jwtOptions = new JWTOptions();
        jwtOptions.setExpiresInSeconds(EXPIRE_IN_SECONDS);
        jwtOptions.setAlgorithm(ALGORITHM);
        return provider.generateToken(json, jwtOptions);
    }

    public void authenticate(JsonObject token) {

        provider.authenticate(token, result -> {
            if(result.succeeded()) {
                System.out.println("result " + result.result().principal());
                System.out.println(result.result().attributes());
            } else {
                result.cause().printStackTrace();
            }
        });
    }

    public JWTAuth getProvider() {
        return provider;
    }
}
