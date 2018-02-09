package uy.panchobot.utils;
import java.text.Normalizer;

public class StringManager {

    private static StringManager singletonStringManagerInstance = null;

    public String findMeAPhrase(String messageLastWord)
    {
        FileManager fileManager = FileManager.getInstance();
        String[] lines =  fileManager.readFileLines();

        for (String line:lines) {
            String lastWord = this.processLine(line);
            if(this.twoWordsEndAreEquals(messageLastWord, lastWord)){
                return line;
            }
        }
        return null;
    }

    private boolean twoWordsEndAreEquals(String firstWord, String secondWord){
        if (firstWord == null || secondWord == null || firstWord.length() < 3 || secondWord.length() < 3) {
            return false;
        } else {
           if((firstWord.substring(firstWord.length() - 3)).equals((secondWord.substring(secondWord.length() - 3))))
               return true;
        }
        return false;
    }

    public String processLine(String message){
        String lastWord = getLastWord(message);//Getting the last word of the message and uppercase it
        lastWord = replaceLocaleChars(lastWord); //Replace and delete tildes and non basic chars.
        lastWord = deleteDuplicateChars(lastWord); //Deleting duplicate chars

        return lastWord;
    }

    public String processMessage(String message){
        String lastWord = this.processLine(message);
        return this.findMeAPhrase(lastWord);
    }

    private String replaceLocaleChars(String word){
        //Replace special chars
        return (Normalizer.normalize(word, Normalizer.Form.NFD)).replaceAll("[^\\p{ASCII}]", "").replaceAll("[^A-Za-z0-9]", "");
    }

    private String deleteDuplicateChars(String word){
        //Delete duplicated chars
        if ( word.length() <= 1 )
            return word;
        if( word.substring(1,2).equals(word.substring(0,1)) )
            return deleteDuplicateChars(word.substring(1));
        else
            return word.substring(0,1) + deleteDuplicateChars(word.substring(1));
    }


    private String getLastWord(String text){
        //capitalize and return last word
        return (text.substring(text.lastIndexOf(" ")+1)).toUpperCase().trim();
    }

    protected StringManager() {}

    public static StringManager getInstance() {
        if(singletonStringManagerInstance == null) {
            singletonStringManagerInstance = new StringManager();
        }
        return singletonStringManagerInstance;
    }

    public void addPhraseToFile(String message){
        message = this.preProcessPhraseBeforeAdd(message);
        if(message != null) {
            FileManager fm = FileManager.getInstance();
            fm.writeFile(message);
        }
    }

    private String preProcessPhraseBeforeAdd(String message){
        message = cutOffPhraseMaxLength(message); //Max length of phrase.
        message = cutOffCommand(message);
        //Include here any others validation to new messages.
        return message;
    }

    private String cutOffPhraseMaxLength(String message){
        if(message.length()>50)
            return (message.substring(0, 50)).trim();
        return message.trim();
    }

    private String cutOffCommand(String message){
        if(message.contains(" "))
            return message.substring(message.indexOf(" ")).trim();
        return null;
    }

    public String getCleanCommand(String message){
        if(message.contains(" "))
            message = message.substring(0, message.indexOf(" "));
        return message.toUpperCase().trim();
    }
}
