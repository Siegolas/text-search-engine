package com.textsearchengine.structure;

/**
 * Class useful to represent the words contained by a file. Contains @word and  @file properties.
 */
public class WordInMemory {
    private String word;
    private String file;

    public WordInMemory(String word, String file) {
        this.word = word;
        this.file = file;
    }

    public String getWord() {
        return word;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "WordInMemory{" +
                "word='" + word + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
