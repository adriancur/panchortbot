
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PanchoBot extends TelegramLongPollingBot {

    private static final String BOT_SETTING_USERNAME = "Panchortbot";
    private static final String BOT_SETTING_TOKEN = "500229132:AAGwulp-Gd9N47tAx-wAPqbneYC0OXYTYZU";
    private static final String MESSAGE_START = "El panchobot esta activo y te va a responder cuando le pinte.";
    private static final String MESSAGE_STOP = "El panchobot se mantendra calladito.";
    private static final String COMMAND_START = "/start";
    private static final String COMMAND_STOP = "/stop";
    private static final String COMMAND_BARDOON = "/modoBardoOn";
    private static final String COMMAND_BARDOOFF = "/modoBardoOff";
    private static boolean BOT_SETTING_ENABLED = true;
    private static boolean BOT_SETTING_RANDOMIZE_CHANCE = true;

    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals(COMMAND_START)) {
                //Send welcome message and enable bot.
                sendAndExecuteMessage(chat_id, MESSAGE_START);
                BOT_SETTING_ENABLED = true;
            } else if (update.getMessage().getText().equals(COMMAND_STOP)) {
                //Disable bot
                sendAndExecuteMessage(chat_id, MESSAGE_STOP);
                BOT_SETTING_ENABLED = false;
            } else {
                if (BOT_SETTING_ENABLED) {
                    if (update.getMessage().getText().equals(COMMAND_BARDOON)) {
                        //Mode bardo on, response every message.
                        BOT_SETTING_RANDOMIZE_CHANCE = false;
                    } else if (update.getMessage().getText().equals(COMMAND_BARDOOFF)) {
                        //Mode bardo off, normal response.
                        BOT_SETTING_RANDOMIZE_CHANCE = true;
                    }

                    if (BOT_SETTING_RANDOMIZE_CHANCE) {
                        //Validate if random response is enabled
                        if (Math.random() > 0.93) {
                            sendAndExecuteMessage(chat_id, getPhoneticString(update.getMessage().getText()));
                        }
                    } else {
                        //Response every message
                        sendAndExecuteMessage(chat_id, getPhoneticString(update.getMessage().getText()));
                    }
                }
            }
        }
    }

    private String getPhoneticString(String message) {
        //Direct message to bot.
        return "respuesta";
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

    private void sendAndExecuteMessage(long chat_id, String message_text) {
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
