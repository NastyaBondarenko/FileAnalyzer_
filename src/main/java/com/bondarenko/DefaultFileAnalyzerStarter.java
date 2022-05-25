package com.bondarenko;

import com.bondarenko.fileanalyzer.DefaultFileAnalyzer;
import com.bondarenko.fileinformation.FileInformation;

public class DefaultFileAnalyzerStarter {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Make sure you pass two arguments: file path and keyword");
        }
        DefaultFileAnalyzer defaultFileAnalyzer = new DefaultFileAnalyzer();

        String path = args[0];
        String word = args[1];

        FileInformation fileInformation = defaultFileAnalyzer.analyze(path, word);
        System.out.println(defaultFileAnalyzer.formatResult(fileInformation));
    }
}
