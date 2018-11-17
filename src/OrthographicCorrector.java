import javafx.util.Pair;

import java.util.*;

public class OrthographicCorrector {
    private static TreeMap<String,Integer> allWords;

    static {
        allWords = new TreeMap<>();
        FileHandler.getDictionaryWords(allWords);
        FileHandler.getVocabularyWords(allWords);
    }

    public  OrthographicCorrector(){}

    /**
     * This Method tries to correct it's terms
     * @param query the query to correct
     * @return a corrected query
     */
    public static String correctOrthography(String query){
       LinkedHashMap<String,String> comparator = new LinkedHashMap<>();
       query = query.toLowerCase();
       //Put query into map
       putQueryToMap(comparator,query);

        ///Preprocess query
        String purifiedQuery = Preprocessor.preprocess(query);

        //Search Corrected terms in the query
        searchCorrectedTerms(purifiedQuery, comparator);

       // Remake the query with corrected terms and return it
        return remakeQuery(comparator,query).trim();
    }

    /**
     * This method puts a preprocessed query into a Map.
     * @param map A map.
     * @param query the query.
     */
    private static void putQueryToMap(LinkedHashMap<String,String> map, String query){
        String[] splitOriginalQuery = query.toLowerCase().split("\\s+");

        for (String word : splitOriginalQuery) {
            map.put(word,"");
        }

    }

    /**
     * This Method search if a terms is in the vocabulary or a Spanish dictionary, if that's the case it ignores that term.
     * Otherwise the method tries to search another word with a low distance.
     * @param purifiedQuery The query with out stop words.
     * @param map A map with the complete query
     */
    private static void searchCorrectedTerms(String purifiedQuery, LinkedHashMap<String, String> map){
        String[] splitQuery = purifiedQuery.split("\\s+");
        for (String word : splitQuery) {
            if(!allWords.containsKey(word)){
                for (String vocabularyTerm : allWords.keySet()) {
                    int distance = DistanceCalculator.computeLevenshteinDistance(vocabularyTerm,word);
                    allWords.put(vocabularyTerm , distance);
                }
                map.put(word,entriesSortedByValues(allWords).first().getKey());
            }
        }
    }

    /**
     * This Method remakes a query to replace incorrect word with correct ones.
     * @param map a map with the query
     * @return A string with a correct query
     */
    private static String remakeQuery(LinkedHashMap<String, String> map, String originalQuery){
        StringBuilder correctedQuery = new StringBuilder();
        String [] splitQuery = originalQuery.split("\\s+");
        for (String word : splitQuery) {
            if (map.get(word).compareTo("") == 0){
                correctedQuery.append(word).append(" ");
            }else{
                correctedQuery.append(map.get(word)).append(" ");
            }
        }
        return correctedQuery.toString();
    }

    /**
     * This method sorts a HashMap by the values
     * @param map The map to sort
     * @param <K> First Type
     * @param <V> Second Type
     * @return A sorted Set.
     */
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
