package com.textsearchengine.processors;


import com.textsearchengine.structure.WordInMemory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class FileProcessor {
    private final String path;

    public FileProcessor(String path) {
        this.path = path;
    }

    /**
     * Iterates through the files located under the specified path and obtains
     * a Map in which the key is the word and the value is the List of filenames
     * of the files in which the word appears.
     *
     * @return
     */
    public Map<String, List<String>> processFilesInPath() {
        try {
            return Files.walk(Paths.get(path))
                    .filter(path -> !Files.isDirectory(path))
                    .sorted(Comparator.comparing(path -> path.getFileName()))
                    .map(this::fileToMemory)
                    .flatMap(Collection::stream) //Convert from Stream<List<WordInFile>> to Stream<WordInFile>
                    .filter(element -> !element.getWord().isEmpty()) //Removes WordInFile with empty word
                    .collect(toMap(WordInMemory::getWord, WordInMemory::getFilenameList, this::mergeFunctionForSameKeyInMap)); //Converts Stream<WordInFile> to Map<String, List<String>>
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that specifies what criteria to apply in map values in case that
     * keys of a map collide. Performs a concatenation of Lists applying distinct
     * so it doesn't produce duplicates in the resultant List.
     *
     * @param currentList
     * @param newList
     * @return
     */
    private List<String> mergeFunctionForSameKeyInMap(List<String> currentList, List<String> newList) {
        return Stream.concat(currentList.stream(), newList.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Iterates through the lines that a file contains, obtains the words and
     * then obtains a List of WordInMemory objects. Each WordInMemory represents
     * a word and a List of filenames of the files in which the word appears.
     * <p>
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
                    .map(word -> new WordInMemory(word, Arrays.asList(path.toString()))) //Obtain word-in-files representation
                    .collect(Collectors.toList()); //Collect as List
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
