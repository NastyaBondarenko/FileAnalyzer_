package com.bondarenko.fileanalyzer;

import com.bondarenko.fileinformation.FileInformation;

import java.util.List;

public interface FileAnalyzer {

    String readContent(String path);

    List<String> splitIntoSentences(String content);

    List<String> filter(List<String> sentences, String word);

    int countWords(List<String> text, String word);

    FileInformation analyze(String path, String word);
}
