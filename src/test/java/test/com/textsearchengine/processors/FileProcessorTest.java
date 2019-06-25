package test.com.textsearchengine.processors;

import com.textsearchengine.processors.FileProcessor;
import com.textsearchengine.structure.WordInMemory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FileProcessorTest {
    private FileProcessor fileProcessor;

    @Before
    public void setUp() {
        fileProcessor = new FileProcessor("src/test/resources/testFiles");
    }

    @Test
    public void processFilesInPath() {
        Map<String, String> wordsInMemory =  fileProcessor.processFilesInPath();
        wordsInMemory.entrySet().size();

        //Check total of words
        Assert.assertEquals(4, wordsInMemory.size());

        String filesMatchingWordFile = wordsInMemory.get("file");
        Assert.assertEquals(3, filesMatchingWordFile.split("\\|").length);

        String filesMatchingWordFirst = wordsInMemory.get("first");
        Assert.assertEquals(1, filesMatchingWordFirst.split("\\|").length);
    }

    @Test
    public void fileToMemory() {
        String filePath = "src/test/resources/testFiles/filename1";
        Path path = Paths.get(filePath);
        List<WordInMemory> wordInMemoryList = fileProcessor.fileToMemory(path);

        //Check total of words
        Assert.assertEquals(wordInMemoryList.size(), 2);

        //Check words appearances
        Assert.assertEquals(wordInMemoryList.get(0).getWord(),"first");
        Assert.assertEquals(wordInMemoryList.get(0).getFile(),filePath);
        Assert.assertEquals(wordInMemoryList.get(1).getWord(),"file");
        Assert.assertEquals(wordInMemoryList.get(1).getFile(),filePath);
    }
}