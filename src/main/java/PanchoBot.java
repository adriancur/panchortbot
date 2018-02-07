import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PanchoBot extends TelegramLongPollingBot {

    private static final String BOT_SETTING_USERNAME = "Panchortbot";
    private static final String BOT_SETTING_TOKEN = "500229132:AAGwulp-Gd9N47tAx-wAPqbneYC0OXYTYZU";
    private static final String MESSAGE_START = "El panchobot esta activo y te va a responder cuando le pinte.";
    private static final String COMMAND_START = "/start";

    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals(COMMAND_START)) {
                sendAndExecuteMessage(chat_id, MESSAGE_START);
            }else{
                //Direct message to bot.
                if(update.getMessage().getText().equals("mango")){
                    sendAndExecuteMessage(chat_id, "el orto te remango");
                }
                if(update.getMessage().getText().equals("doctor")){
                    sendAndExecuteMessage(chat_id, "el culo con ardor");
                }
            }
        }
    }

    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return BOT_SETTING_USERNAME;
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return BOT_SETTING_TOKEN;
    }

    private void sendAndExecuteMessage(long chat_id, String message_text){
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(message_text);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}