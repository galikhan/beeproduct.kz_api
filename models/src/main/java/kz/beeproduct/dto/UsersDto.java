package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.UsersRecord;

public class UsersDto {

    public String login;
    public String phone;
    public String address;
    public Integer entrance;
    public Integer floor;
    public Integer flat;
    public String session;

    public UsersDto() {
    }

    public UsersDto(UsersRecord record) {
        this.login = record.getLogin_();
        this.phone = record.getPhone_();
        this.address = record.getAddress_();
        this.entrance = record.getEntrance_();
        this.flat = record.getFlat_();
        this.floor = record.getFloor_();
        this.session = record.getSession_();

    }
}
