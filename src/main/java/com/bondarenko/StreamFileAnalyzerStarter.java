package com.bondarenko;

import com.bondarenko.fileanalyzer.streamfileanalyzer.StreamFileAnalyzer;
import com.bondarenko.fileinformation.FileInformation;

public class StreamFileAnalyzerStarter {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Make sure you pass two arguments: file path and keyword");
        }
        StreamFileAnalyzer streamFileAnalyzer = new StreamFileAnalyzer();

        String path = args[0];
        String word = args[1];

        FileInformation fileInformation = streamFileAnalyzer.analyze(path, word);
        System.out.println(streamFileAnalyzer.formatResult(fileInformation));
    }
}
