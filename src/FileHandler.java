import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class FileHandler {

    public static void getVocabularyWords(TreeMap<String,Integer> allWords){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("resources/Vocabulary.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split("\\s");
                allWords.put(split[0].trim(),0);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getDictionaryWords(TreeMap<String,Integer> allWords){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("resources/dictionary.txt"));
            String line = reader.readLine();
            while (line != null) {
                allWords.put(line.trim(),0);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
