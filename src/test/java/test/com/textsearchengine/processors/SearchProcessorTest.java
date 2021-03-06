package test.com.textsearchengine.processors;

import com.textsearchengine.processors.SearchProcessor;
import com.textsearchengine.structure.TextSearchResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchProcessorTest {
    private SearchProcessor searchProcessor;

    @Before
    public void setUp() {
        Map<String, List<String>> wordsInMemory = new HashMap();
        //Note: add words in upper case to wordsInMemory map as SearchProcessor searches for upper case words
        wordsInMemory.put("FIRST", Arrays.asList("filename1"));
        wordsInMemory.put("SECOND", Arrays.asList("filename2"));
        wordsInMemory.put("THIRD", Arrays.asList("filename3"));
        wordsInMemory.put("FILENAME", Arrays.asList("filename1", "filename2", "filename3"));

        searchProcessor = new SearchProcessor(wordsInMemory, 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void searchInputWordsOnWordsInMemory() {
        String[] inputWords = "first filename".split("\\W+");

        Map<String, Long> searchResult = searchProcessor.searchInputWordsOnWordsInMemory(inputWords);

        Assert.assertEquals(3, searchResult.size());
        Assert.assertEquals(2L, (long) searchResult.get("filename1"));
        Assert.assertEquals(1L, (long) searchResult.get("filename2"));
        Assert.assertEquals(1L, (long) searchResult.get("filename3"));
        Assert.assertEquals(null, searchResult.get("second"));
    }

    @Test
    public void sortAndLimitMap() {
        int maxEntries = 2;
        Map<String, Long> unorderedMap = new LinkedHashMap();
        unorderedMap.put("file_with_2_matches", 2L);
        unorderedMap.put("file_with_1_match", 1L);
        unorderedMap.put("file_with_3_matches", 3L);

        Map<String, Long> orderedMap = searchProcessor.sortAndLimitMap(unorderedMap, maxEntries);

        //Check that number of entries is 2 and order is applied. One entry has been skipped due to the maxEntries applied
        Assert.assertEquals(orderedMap.size(), maxEntries);
        Assert.assertEquals(3L, orderedMap.values().toArray()[0]);
        Assert.assertEquals(2L, orderedMap.values().toArray()[1]);
    }

    @Test
    public void getResultsFromMap() {
        int totalWords = 3;
        Map<String, Long> orderedMap = new LinkedHashMap();
        orderedMap.put("file_with_3_matches", 3L);
        orderedMap.put("file_with_2_matches", 2L);
        orderedMap.put("file_with_1_match", 1L);

        List<TextSearchResult> textSearchResultList = searchProcessor.getResultsFromMap(orderedMap, totalWords);

        Assert.assertEquals(textSearchResultList.size(), orderedMap.size());
        Assert.assertEquals(textSearchResultList.get(0).getPercentage(), ((float) orderedMap.get("file_with_3_matches") / totalWords) * 100, 0.0f);
        Assert.assertEquals(textSearchResultList.get(1).getPercentage(), ((float) orderedMap.get("file_with_2_matches") / totalWords) * 100, 0.0f);
        Assert.assertEquals(textSearchResultList.get(2).getPercentage(), ((float) orderedMap.get("file_with_1_match") / totalWords) * 100, 0.0f);
    }
}