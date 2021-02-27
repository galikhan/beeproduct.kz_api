package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.TgUserRecord;

public class TgUserDto {

    public Long id;
    public Integer chatId;
    public String username;
    public String firstname;
    public String role;


    public TgUserDto() {
    }

    public TgUserDto(TgUserRecord record) {

        this.id = record.getId_();
        this.chatId = record.getChatId_();
        this.firstname = record.getFirstName_();
        this.username = record.getUsername_();
        this.role = record.getRole_();

    }
}

