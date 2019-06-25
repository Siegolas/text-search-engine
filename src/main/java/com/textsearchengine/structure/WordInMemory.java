package com.textsearchengine.structure;

import java.util.List;

/**
 * Class useful to represent the appearances of a word in a list of files. Contains @word and @filenameList properties.
 */
public class WordInMemory {
    private String word;
    private List<String> filenameList;

    public WordInMemory(String word, List<String> filenameList) {
        this.word = word;
        this.filenameList = filenameList;
    }

    public String getWord() {
        return word;
    }

    public List<String> getFilenameList() {
        return filenameList;
    }

    @Override
    public String toString() {
        return "WordInMemory{" +
                "word='" + word + '\'' +
                ", filenameList='" + filenameList + '\'' +
                '}';
    }
}
