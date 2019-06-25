package test.com.textsearchengine.processors;

import com.textsearchengine.processors.FileProcessor;
import com.textsearchengine.structure.WordInMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileProcessorTest {
    private FileProcessor fileProcessor;

    @Before
    public void setUp() {
        fileProcessor = new FileProcessor("src/test/resources/testFiles");
    }

    @Test
    public void processFilesInPath() {
        Map<String, List<String>> wordsInMemory = fileProcessor.processFilesInPath();

        //Check total of words
        Assert.assertEquals(4, wordsInMemory.size());

        //Check words appearances
        List<String> filesMatchingWordFile = wordsInMemory.get("FILE");
        Assert.assertEquals(3, filesMatchingWordFile.size());
        List<String> filesMatchingWordFirst = wordsInMemory.get("FIRST");
        Assert.assertEquals(1, filesMatchingWordFirst.size());
    }

    @Test
    public void fileToMemory() {
        String filePath = "src/test/resources/testFiles/filename1";
        Path path = Paths.get(filePath);
        List<WordInMemory> wordInMemoryList = fileProcessor.fileToMemory(path);

        //Check total of words
        Assert.assertEquals(wordInMemoryList.size(), 3);

        //Check words appearances
        Assert.assertEquals(wordInMemoryList.get(0).getWord(), "FIRST");
        Assert.assertEquals(wordInMemoryList.get(0).getFilenameList().size(), 1);
        Assert.assertEquals(wordInMemoryList.get(0).getFilenameList().get(0), filePath);
        Assert.assertEquals(wordInMemoryList.get(1).getWord(), "FILE");
        Assert.assertEquals(wordInMemoryList.get(1).getFilenameList().size(), 1);
        Assert.assertEquals(wordInMemoryList.get(1).getFilenameList().get(0), filePath);
    }
}