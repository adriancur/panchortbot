
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import uy.panchobot.utils.StringManager;

import java.util.HashMap;

public class PanchoBot extends TelegramLongPollingBot {

    private static final String BOT_SETTING_USERNAME = "Panchortbot";
    private static final String BOT_SETTING_TOKEN = "500229132:AAGwulp-Gd9N47tAx-wAPqbneYC0OXYTYZU";
    private static final String MESSAGE_START = "El panchobot quedo activo y te va a responder cuando le pinte.";
    private static final String MESSAGE_STOP = "**Gracias por el asado!**";
    private static final String COMMAND_START = "/START";
    private static final String COMMAND_STOP = "/STOP";
    private static final String COMMAND_BARDOON = "/STARTBARDO";
    private static final String COMMAND_BARDOOFF = "/STOPBARDO";
    private static final String COMMAND_ADD= "/ADD";
    private HashMap<Long,Boolean> BOT_SETTING_RANDOMIZE_CHANCE = new HashMap<Long,Boolean>();
    private HashMap<Long,Boolean> BOT_CHAT_ENABLED = new HashMap<Long,Boolean>();

    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            if(message.startsWith("/")){
                //Is a command
                String command = StringManager.getInstance().getCleanCommand(message);

                if (command.equals(COMMAND_START)) {
                    //Send welcome message and enable bot.
                    sendAndExecuteMessage(chat_id, MESSAGE_START);
                    BOT_CHAT_ENABLED.put(chat_id,true);
                } else if (command.equals(COMMAND_STOP)) {
                    //Disable bot
                    sendAndExecuteMessage(chat_id, MESSAGE_STOP);
                    BOT_CHAT_ENABLED.put(chat_id,false);
                }else if (BOT_CHAT_ENABLED.get(chat_id) != null && BOT_CHAT_ENABLED.get(chat_id).booleanValue() == true) {
                    if (command.equals(COMMAND_BARDOON)) {
                        //Mode bardo on, response every message.
                        BOT_SETTING_RANDOMIZE_CHANCE.put(chat_id,true);
                    } else if (command.equals(COMMAND_BARDOOFF)) {
                        //Mode bardo off, normal response.
                        BOT_SETTING_RANDOMIZE_CHANCE.put(chat_id,false);
                    }else if (command.equals(COMMAND_ADD)) {
                        //Add phrase to file.
                        this.addPhraseToFile(message);
                    }
                }
            }else{
                //Is a message
                if (BOT_CHAT_ENABLED.get(chat_id) != null && BOT_CHAT_ENABLED.get(chat_id).booleanValue() == true) {
                    if (BOT_SETTING_RANDOMIZE_CHANCE.get(chat_id) == null || BOT_SETTING_RANDOMIZE_CHANCE.get(chat_id).booleanValue() == false) {
                        //Validate if random response is enabled
                        if (Math.random() > 0.93) {
                            String responseMessage = getPhoneticString(message);
                            if (responseMessage != null)
                                sendAndExecuteMessage(chat_id, responseMessage);
                        }
                    } else {
                        //Response every message
                        String responseMessage = getPhoneticString(message);
                        if (responseMessage != null)
                            sendAndExecuteMessage(chat_id, responseMessage);
                    }
                }
            }
        }
    }

    private void addPhraseToFile(String message){
        StringManager sm = StringManager.getInstance();
        sm.addPhraseToFile(message);
    }

    private String getPhoneticString(String message) {
        StringManager sm = StringManager.getInstance();
        return sm.processMessage(message);
    }

    public String getBotUsername() {
        // Return bot username
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
