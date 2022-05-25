package com.bondarenko.fileanalyzer;

import com.bondarenko.fileinformation.FileInformation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFileAnalyzer implements FileAnalyzer {

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
        try (Stream<String> lineStream = Files.lines(Paths.get(path))) {
            content = String.join("", lineStream.collect(Collectors.toList()));
            return content;
        } catch (IOException e) {
            throw new RuntimeException("Cant read file by path", e);
        }
    }

    @Override
    public List<String> splitIntoSentences(String content) {
        return Stream.of(content.split("((?<=[.?!]))"))
                .map(String::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> filter(List<String> filteredSentences, String word) {
        return filteredSentences.stream().
                map(String::toLowerCase).
                filter(sentences -> sentences.contains(word)).
                collect(Collectors.toList());
    }

    @Override
    public int countWords(List<String> sentences, String word) {
        return (int) sentences.stream()
                .map(line -> line.split("\\s+"))
                .flatMap(Arrays::stream)
                .filter(t -> t.contains(word))
                .count();
    }

    public String formatResult(FileInformation result) {
        StringBuilder resultOfFileAnalyzer = new StringBuilder("""

                Result of FileAnalyzer:\s
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
