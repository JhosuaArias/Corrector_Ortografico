import javafx.util.Pair;

import java.util.*;

public class OrthographicCorrector {
    private TreeMap<String,Integer> allWords;

    public OrthographicCorrector(){
        this.allWords = new TreeMap<>();
        FileHandler.getDictionaryWords(this.allWords);
        FileHandler.getVocabularyWords(this.allWords);
        System.out.println(this.allWords);
    }

    public String correctOrthography(String query){
       LinkedHashMap<String,String> comparator = new LinkedHashMap<>();

        //Meto
        String[] splitOriginalQuery = query.toLowerCase().split("\\s+");

        for (String word : splitOriginalQuery) {
            comparator.put(word,"");
        }

        ///Preproceso
        String purifiedQuery = Preprocessor.preprocess(query);

        String[] splitQuery = purifiedQuery.split("\\s+");

        for (String word : splitQuery) {
            if(!this.allWords.containsKey(word)){
                for (String vocabularyTerm : this.allWords.keySet()) {
                    int distance = DistanceCalculator.computeLevenshteinDistance(vocabularyTerm,word);
                    this.allWords.put(vocabularyTerm , distance);
                }
                comparator.put(word,entriesSortedByValues(this.allWords).first().getKey());
            }
        }

        String correctedQuery = "";

        for (String word : comparator.keySet()) {
            if (comparator.get(word).compareTo("") == 0){
                correctedQuery += word +" ";
            }else{
                correctedQuery += comparator.get(word) + " ";
            }
        }
        return correctedQuery.trim();
    }

    static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
