package com.textsearchengine.structure;

/**
 * Class useful to represent the text search engine results. Contains @filename, @fileMatches
 * and @totalWords properties. The @toString method returns the filename and the percentage of word
 * matches achieved by the file with name @filename.
 */
public class TextSearchResult {
    private String filename;
    private Long fileMatches;
    private int totalWords;

    public TextSearchResult(String filename, Long fileMatches, int totalWords) {
        this.filename = filename;
        this.fileMatches = fileMatches;
        this.totalWords = totalWords;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFileMatches() {
        return fileMatches;
    }

    public void setFileMatches(Long fileMatches) {
        this.fileMatches = fileMatches;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public float getPercentage() {
        return ((float) fileMatches / totalWords) * 100;
    }

    /**
     * Returns the filename and the percentage of word matches achieved by the file with name @filename
     *
     * @return
     */
    @Override
    public String toString() {
        return filename + " : " + getPercentage() + "%";
    }
}
