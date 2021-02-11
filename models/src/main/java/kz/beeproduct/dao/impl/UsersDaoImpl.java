package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.UsersDao;
import kz.beeproduct.dto.UsersDto;
import kz.beeproduct.model.tables.Users;
import kz.beeproduct.model.tables.records.UsersRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;

import static kz.beeproduct.model.tables.Users.USERS;

public class UsersDaoImpl implements UsersDao {

    private DSLContext ctx;
    private Logger log = LogManager.getLogger(UsersDaoImpl.class);

    public UsersDaoImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public UsersDto save(UsersDto user) {

        log.info("session {}", user.session);
        UsersRecord record = _findBySession(user.session);
        record = _prepare(record, user);
        record.update();

        return new UsersDto(record);
    }

    @Override
    public UsersDto create(UsersDto user) {

        UsersRecord record = ctx.newRecord(USERS);
        record = _prepare(record, user);
        record.insert();

        return new UsersDto(record);
    }

//    @Override
//    public UsersDto findByLogin(String login) {
//        return null;
//    }

    @Override
    public UsersDto findBySession(String sessionId) {

        UsersRecord record = _findBySession(sessionId);
        return record == null? null:new UsersDto(record);

    }

    private UsersRecord _findBySession(String sessionId) {

        return ctx.selectFrom(USERS).where(USERS.SESSION_.eq(sessionId)).fetchOne();

    }

    private UsersRecord _prepare(UsersRecord record, UsersDto user) {

        record.setAddress_(user.street);
        record.setEntrance_(user.entrance);
        record.setFlat_(user.flat);
        record.setFloor_(user.floor);
        record.setPhone_(user.phone);
        record.setSession_(user.session);
        record.setLogin_(user.login);
        record.setFullname_(user.fullname);
        return record;

    }
}
