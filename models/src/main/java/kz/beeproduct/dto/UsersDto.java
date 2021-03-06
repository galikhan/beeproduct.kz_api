package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.UsersRecord;

public class UsersDto {

    public String login;
    public String phone;
    public String street;
    public Integer entrance;
    public Integer floor;
    public Integer flat;
    public String fullname;
    public String session;
    public String paymentType;

    public UsersDto() {
    }

    public UsersDto(UsersRecord record) {
        this.login = record.getLogin_();
        this.phone = record.getPhone_();
        this.street = record.getAddress_();
        this.entrance = record.getEntrance_();
        this.flat = record.getFlat_();
        this.floor = record.getFloor_();
        this.session = record.getSession_();
        this.fullname = record.getFullname_();
        this.paymentType = record.getPaymentType_();
    }

    public static String getInfo(UsersDto user) {
        return user != null ? user.fullname + " " + user.phone
                + " (" + user.street + ", " + user.flat + " кв, "
                + user.floor + " этаж, " + user.entrance + " подьезд)" : "";
    }
}
