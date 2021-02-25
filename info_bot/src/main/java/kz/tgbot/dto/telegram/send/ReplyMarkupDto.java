package kz.tgbot.dto.telegram.send;

import java.util.Vector;

public class ReplyMarkupDto {

    public Vector<Vector<KeyboardButtonDto>> keyboard;

    public ReplyMarkupDto() {
        this.keyboard = new Vector<>();
    }


    //    resize_keyboard -  Boolean
    //one_time_keyboard - Boolean
    //selective - Boolean
}
