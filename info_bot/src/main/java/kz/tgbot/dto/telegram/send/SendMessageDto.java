package kz.tgbot.dto.telegram.send;

public class SendMessageDto {

    public Integer chat_id;
    public String text;
//    public ReplyMarkupDto reply_markup;

    public SendMessageDto() {
    }

    /*
    parse_mode String
    entities	Array of MessageEntity
    disable_web_page_preview	Boolean
    disable_notification	Boolean
    reply_to_message_id	Integer
    allow_sending_without_reply	Boolean
    */
}
