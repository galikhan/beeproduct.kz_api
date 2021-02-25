//package kz.gparkinfobot.conversation;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Contact;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//
//public class Conversation extends TelegramLongPollingBot {
//
//    private Logger log = LogManager.getLogger(Conversation.class);
//
//    @Override
//    public String getBotUsername() {
//        return "gpark_info_bot";
//    }
//
//    @Override
//    public String getBotToken() {
//        return "1698913294:AAHKHSXI7ecR4D2V14GZq7Y1k_CBExsv2JA";
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//
//        try {
//            log.info("info come {} {}", update.getMessage().getText());
//
//            Long chatId = update.getMessage().getChatId();
//            String message = update.getMessage().getText();
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId + "");
//            log.info("info chatId - {},  userId - {}", chatId, update.getMessage().getFrom().getId());
//
//            if(message == null) {
//
//                Contact contact = update.getMessage().getContact();
//                log.info("contact data {}", contact.getFirstName() + " " + contact.getLastName() + " : " + contact.getPhoneNumber());
//
//            } else {
//
//
//                if (message.equals("/start")) {
//
//                    //save contact
//
//                    KeyboardButton button = new KeyboardButton();
//                    button.setRequestContact(true);
//                    button.setText("Отправить контакт");
//
//                    KeyboardRow keyboardRow = new KeyboardRow();
//                    keyboardRow.add(button);
//
//                    List<KeyboardRow> keyboardRows = new ArrayList<>();
//                    keyboardRows.add(keyboardRow);
//
//                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                    replyKeyboardMarkup.setKeyboard(keyboardRows);
//
//                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
//
//                } else {
//
//                    sendMessage.setText("else");
//
//
//                }
//            }
//
//            int execute = execute(sendMessage).getMessageId();
//            log.info("msg sent id {}", execute);
//
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
