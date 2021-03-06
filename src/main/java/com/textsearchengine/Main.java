package com.textsearchengine;


import com.textsearchengine.constants.Constants;
import com.textsearchengine.processors.FileProcessor;
import com.textsearchengine.processors.SearchProcessor;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        final File indexableDirectory = new File(args[0]);
        if (!indexableDirectory.isDirectory()) {
            throw new IllegalArgumentException(args[0] + " is not a directory.");
        }

        String path = args[0];
        Map<String, List<String>> wordsInMemory = new FileProcessor(path).processFilesInPath();
        SearchProcessor searchProcessor = new SearchProcessor(wordsInMemory, Constants.MAX_NUMBER_OF_RESULTS);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("search>");
            String inputLine = scanner.nextLine();
            if (inputLine.equals(":quit")) {
                return;
            }
            searchProcessor.searchAndPrint(inputLine);
        }
    }
}
