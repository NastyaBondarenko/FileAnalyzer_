package com.bondarenko.fileanalyzer;

import com.bondarenko.fileanalyzer.FileAnalyzer;
import com.bondarenko.fileinformation.FileInformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultFileAnalyzer implements FileAnalyzer {
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("((?<=[.?!]))");

    @Override
    public FileInformation analyze(String path, String word) {
        validatePath(path);
        validateWord(word);

        String content = readContent(path);
        List<String> sentences = splitIntoSentences(content);
        List<String> filteredSentences = filter(sentences, word);
        int wordCount = countWords(filteredSentences, word);
        return new FileInformation(word, filteredSentences, wordCount);
    }

    @Override
    public String readContent(String path) {
        String content;
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            content = new String(fileInputStream.readAllBytes());
        } catch (IOException exception) {
            throw new RuntimeException("Cant read file by path");
        }
        return content;
    }

    @Override
    public List<String> splitIntoSentences(String content) {
        String[] sentences = SENTENCE_PATTERN.split(content);
        List<String> trimmedSentences = new ArrayList<>();
        for (String sentence : sentences) {
            trimmedSentences.add(sentence.trim());
        }
        return trimmedSentences;
    }

    @Override
    public List<String> filter(List<String> sentences, String word) {
        List<String> searchSentences = new ArrayList<>();
        for (String sentence : sentences) {
            String lowerSentence = sentence.toLowerCase();
            if (lowerSentence.contains(word)) {
                searchSentences.add(lowerSentence);
            }
        }
        return searchSentences;
    }

    @Override
    public int countWords(List<String> text, String word) {
        Pattern pattern = Pattern.compile("\\b" + word + "\\b");
        int count = 0;
        for (String sentences : text) {
            Matcher matcher = pattern.matcher(sentences);
            while (matcher.find()) {
                count++;
            }
        }
        return count;
    }

    public String formatResult(FileInformation result) {
        StringBuilder resultOfFileAnalyzer = new StringBuilder("""

                Result of file analyzer:\s
                In the text,the word (""");
        resultOfFileAnalyzer.append(result.getWord());
        resultOfFileAnalyzer.append(") occurs ");
        resultOfFileAnalyzer.append(result.getCount());
        resultOfFileAnalyzer.append(" times.");
        resultOfFileAnalyzer.append(" All the sentences with this word:\n");
        for (String sentence : result.getSentences()) {
            resultOfFileAnalyzer.append(sentence).append("\r\n");
        }
        return resultOfFileAnalyzer.toString();
    }

    private void validatePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("Path " + path + " is not existing.Provide a valid path");
        }
    }

    private void validateWord(String word) {
        if (word == null) {
            throw new NullPointerException("The word is blank. Provide a valid word");
        }
    }
}



