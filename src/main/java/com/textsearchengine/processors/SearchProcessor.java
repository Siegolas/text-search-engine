package com.textsearchengine.processors;


import com.textsearchengine.structure.TextSearchResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class SearchProcessor {
    private final Map<String, List<String>> wordsInMemory;
    private final int maxResults;

    public SearchProcessor(Map<String, List<String>> wordsInMemory, int maxResults) {
        this.wordsInMemory = wordsInMemory;
        this.maxResults = maxResults;
    }

    /**
     * Performs the call to the appropriate methods to apply the search words
     * on the @wordsInMemory Object. It searches the words, obtains the results,
     * sorts and limits the results, obtains the results objects and prints it.
     *
     * @param inputLine
     */
    public void searchAndPrint(String inputLine) {
        String[] inputWords = inputLine.split("\\W+");
        int totalWords = inputWords.length;

        Map<String, Long> map = this.searchInputWordsOnWordsInMemory(inputWords);
        Map<String, Long> orderedMap = this.sortAndLimitMap(map, maxResults);
        List<TextSearchResult> textSearchResultList = this.getResultsFromMap(orderedMap, totalWords);
        this.printResults(textSearchResultList);
    }

    /**
     * Iterates through the searched words, obtains the total list of filenames
     * matching the input words and then collects the results grouping them as a
     * Map in which the key is they filename and the value is the number of matched
     * words made by this file.
     * <p>
     * The process includes the conversion to upper case of the words.
     *
     * @param inputWords
     * @return
     */
    public Map<String, Long> searchInputWordsOnWordsInMemory(String[] inputWords) {
        Map<String, Long> wordFileMatchingMap = Arrays.stream(inputWords)
                .map(word -> word.toUpperCase()) //Convert to upper case
                .map(word -> wordsInMemory.get(word)) //Get value for this key in wordsInMemory object
                .filter(Objects::nonNull) //Filter words not found in wordsInMemory keys
                .flatMap(Collection::stream) //Flatten object obtaining a stream of Strings
                .collect(groupingBy(Function.identity(), counting())); //Collect and group obtaining a Map of {filename}:{matched words}
        return wordFileMatchingMap;
    }

    /**
     * Sorts and limits the Map of {filename}:{matched words}.
     *
     * @param map
     * @param maxEntries
     * @return
     */
    public Map<String, Long> sortAndLimitMap(Map<String, Long> map, int maxEntries) {
        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) //Sort by {matched words} in descendant order
                .limit(maxEntries) //Limit the quantity of entries
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new)); //Collect as a LinkedHashMap to preserve order
    }

    /**
     * Obtains a List of TextSearchResult objects from a Map of {filename}:{matched words}.
     *
     * @param map
     * @param totalWords
     * @return
     */
    public List<TextSearchResult> getResultsFromMap(Map<String, Long> map, int totalWords) {
        return map.entrySet().stream().map(entry -> new TextSearchResult(entry.getKey(), entry.getValue(), totalWords)).collect(Collectors.toList());
    }

    /**
     * Iterates through a List of TextSearchResult objects and prints them
     *
     * @param textSearchResultList
     */
    public void printResults(List<TextSearchResult> textSearchResultList) {
        if (!textSearchResultList.isEmpty()) {
            textSearchResultList.stream().forEach(System.out::println);
        } else {
            System.out.println("no matches found");
        }
    }
}
