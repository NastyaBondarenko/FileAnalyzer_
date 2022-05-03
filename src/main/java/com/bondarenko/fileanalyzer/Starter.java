package com.bondarenko.fileanalyzer;

public class Starter {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Make sure you pass two arguments: file path and keyword");
        }

        FileAnalyzer fileAnalyzer = new FileAnalyzer();

        String path = args[0];
        String word = args[1];

        FileInformation fileInformation = fileAnalyzer.analyze(path, word);
        System.out.println(fileAnalyzer.formatResult(fileInformation));
    }
}
