package com.textsearchengine.processors;


import com.textsearchengine.structure.WordInMemory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class FileProcessor {
    private final String path;

    public FileProcessor(String path) {
        this.path = path;
    }

    /**
     * Iterates through the files located under specified path and obtains a Map in which
     * the key is the word and the value is the filename or filenames in which the word appears
     *
     * @return
     */
    public Map<String, String> processFilesInPath() {
        try {
            return Files.walk(Paths.get(path))
                    .filter(path -> !Files.isDirectory(path))
                    .sorted(Comparator.comparing(path -> path.getFileName()))
                    .map(this::fileToMemory)
                    .flatMap(Collection::stream) //Convert from Stream<List<WordInFile>> to Stream<WordInFile>
                    .filter(element -> !element.getWord().isEmpty()) //Removes WordInFile with empty word
                    .collect(toMap(WordInMemory::getWord, WordInMemory::getFile, this::mergeWordInMemoryCollision)); //Convierte Stream<WordInFile> en Map<word,filename(s). En caso de colisión de key, se decide concatenar los values
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that specifies what criteria to apply in map values in case that keys of a map collide.
     * If the value is contained returns the value, if the value is not contained it concatenates it
     * applying a separator character
     *
     * @param currentValue
     * @param newValue
     * @return
     */
    private String mergeWordInMemoryCollision(String currentValue, String newValue) {
        if (currentValue.contains(newValue))
            return currentValue;

        return currentValue + "|" + newValue;
    }

    /**
     * Iterates through the lines that a file contains, obtains the words and then obtains a List of
     * WordInMemory objects. Each WordInMemory represents a word and in which file or files it appears.
     *
     * The process includes the conversion to upper case of the words.
     *
     * @param path
     * @return
     */
    public List<WordInMemory> fileToMemory(Path path) {
        try {
            return Files.lines(path) //Stream of lines in file
                    .flatMap(Pattern.compile("\\W+")::splitAsStream) //Obtain words
                    .map(word -> word.toUpperCase()) //Convert to upper case
                    .map(word -> new WordInMemory(word, path.toString())) //Obtain word-in-file representation
                    .collect(Collectors.toList()); //Collect as List
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
