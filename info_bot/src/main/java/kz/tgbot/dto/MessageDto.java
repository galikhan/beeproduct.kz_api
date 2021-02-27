package kz.tgbot.dto;

public class MessageDto {

    public Integer message_id;
    public FromDto from;
    public ChatDto chat;
    public Integer date;
    public String text;
    public MessageDto reply_to_message;


    public MessageDto() {
    }
}
