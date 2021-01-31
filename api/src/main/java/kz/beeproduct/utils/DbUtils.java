package kz.beeproduct.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class DbUtils {


    private static HikariDataSource dataSource;
    private static Logger log = LoggerFactory.getLogger(DbUtils.class);
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    //    public static void init(JsonObject config) {
    public static void init(String port) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:" + port + "/beeproduct");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("postgres");
        hikariConfig.setMaximumPoolSize(10);
//        hikariConfig.setJdbcUrl(config.getString("jdbc_url"));
//        hikariConfig.setUsername(config.getString("user"));
//        hikariConfig.setPassword(config.getString("password"));
//        hikariConfig.setMaximumPoolSize(config.getInteger("maxPoolSize"));
//        hikariConfig.addDataSourceProperty("cachePrepStmts", config.getString("cachePrepStmts"));
//        hikariConfig.addDataSourceProperty("prepStmtCacheSize", config.getString("prepStmtCacheSize"));
//        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", config.getString("prepStmtCacheSqlLimit"));
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        dataSource = hikariDataSource;
    }


    public static <T> CompletableFuture<T> connectAsync(Function<DSLContext, T> res) {

        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = dataSource.getConnection();
                 DSLContext jooq = DSL.using(conn, SQLDialect.POSTGRES)) {

                T result = res.apply(jooq);
                return result;

            } catch (SQLException e) {
                log.error("error", e);
                return null;
            }
        }, executorService);
    }

    public static <T> T connect(Function<DSLContext, T> res) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             DSLContext jooq = DSL.using(conn, SQLDialect.POSTGRES)) {

            return res.apply(jooq);

        } catch (SQLException e) {
            throw e;
        }
    }

    public static <T> void blocking(Function<DSLContext, T> res, Handler<AsyncResult<T>> handler, Vertx vertx) {

        vertx.executeBlocking(blocking -> {
            try (Connection conn = dataSource.getConnection();
                 DSLContext jooq = DSL.using(conn, SQLDialect.POSTGRES)) {
                T result = res.apply(jooq);
                blocking.complete(result);
            } catch (Exception e) {
                log.error("error", e);
                blocking.fail(e);
            }
        }, result -> {
            handler.handle((AsyncResult<T>) result);
        });

    }
}
