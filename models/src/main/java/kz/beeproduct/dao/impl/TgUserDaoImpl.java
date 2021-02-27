package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.TgUserDao;
import kz.beeproduct.dto.TgUserDto;
import kz.beeproduct.model.Sequences;
import kz.beeproduct.model.tables.records.TgUserRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.stream.Collectors;

import static kz.beeproduct.model.tables.TgUser.TG_USER;

public class TgUserDaoImpl implements TgUserDao {

    private DSLContext ctx;

    public TgUserDaoImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public TgUserDto create(TgUserDto user) {

        TgUserRecord record = ctx.newRecord(TG_USER);
        record.setChatId_(user.chatId);
        record.setFirstName_(user.firstname);
        record.setId_(ctx.nextval(Sequences.HONEYBEE_SEQ));
        record.setUsername_(user.username);
        record.setRole_(user.role);
        record.insert();

        return new TgUserDto(record);
    }

    @Override
    public List<TgUserDto> findByRole(String role) {
        return ctx.selectFrom(TG_USER).where(TG_USER.ROLE_.eq(role)).fetch().stream().map(TgUserDto::new).collect(Collectors.toList());
    }

    @Override
    public TgUserDto findByChatId(Integer chatId) {
        TgUserRecord record = ctx.selectFrom(TG_USER).where(TG_USER.CHAT_ID_.eq(chatId)).fetchOne();
        return record == null ? null : new TgUserDto(record);
    }
}
