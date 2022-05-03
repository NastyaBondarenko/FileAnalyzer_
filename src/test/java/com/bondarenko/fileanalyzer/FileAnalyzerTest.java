package com.bondarenko.fileanalyzer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileAnalyzerTest {
    FileAnalyzer fileAnalyzer = new FileAnalyzer();

    String content = "Listen!One day! What happened?duck tried to kill hunter.It was a very angry duck.Was duck.";

    @Test
    @DisplayName("test Split Into Sentences")
    public void testSplitIntoSentencesWithValidSeparators() {
        List<String> expectedResult = List.of("Listen!", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");
        List<String> actualResult = fileAnalyzer.splitIntoSentences(content);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test Split Into Sentences With Invalid Separators")
    public void testSplitIntoSentencesWithInvalidSeparators() {
        String content = "Listen<>One day$ What happened;duck tried to kill hunter;It was a very angry duck#Was duck@";

        List<String> expectedResult = List.of(content);
        List<String> actualResult = fileAnalyzer.splitIntoSentences(content);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test Split Into Sentences WithOut Separators")
    public void testSplitIntoSentencesWithOutSeparators() {
        String content = "Just content";

        List<String> expectedResult = List.of(content);
        List<String> actualResult = fileAnalyzer.splitIntoSentences(content);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("when Search Sentences By Searched Word then Sentences With This Word Returned")
    public void whenSearchSentences_BySearchedWord_thenSentencesWithThisWordReturned() {
        String word = "duck";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");

        List<String> expectedSentences = List.of("duck tried to kill hunter.", "it was a very angry duck.", "was duck.");
        List<String> actualSentences = fileAnalyzer.filter(initialSentences, word);

        assertEquals(expectedSentences, actualSentences);
    }

    @Test
    @DisplayName("when Search Sentences By Search Word in Empty List then Empty List Returned")
    public void whenSearchSentencesBySearchWord_inEmptyList_thenEmptyListReturned() {
        String word = "duck";

        List<String> initialSentences = Collections.emptyList();
        List<String> actualSentences = fileAnalyzer.filter(initialSentences, word);

        assertEquals(initialSentences, actualSentences);
    }

    @Test
    @DisplayName("when Search Sentences Without Search Word then Empty List Returned")
    public void whenSearchSentences_WithoutSearchWord_thenEmptyListReturned() {
        String word = "notExistingWord";

        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");

        List<String> expectedSentences = Collections.emptyList();
        List<String> actualSentences = fileAnalyzer.filter(initialSentences, word);

        assertEquals(expectedSentences, actualSentences);
    }

    @Test
    @DisplayName("when Count Search Word in Context With One Searched Word then One Returned")
    public void whenCountSearchWord_inContextWithOneSearchedWord_thenOneReturned() {
        String word = "duck";

        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.");

        int expectedCount = 1;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context Without Searched Word then Zero Returned")
    public void whenCountSearchedWord_inContextWithoutSearchedWord_thenZeroReturned() {
        String word = "duck";

        List<String> initialSentences = List.of("Listen", "One day!", "What happened?");

        int expectedCount = 0;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context With More Then One Searched Word then Appropriate Count Returned")
    public void whenCountSearchedWord_inContextWithMoreThenOneSearchedWord_thenAppropriateCountReturned() {
        String word = "duck";

        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");

        int expectedCount = 3;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context With Similar Symbols but Not With Searched Word then Result Is Zero")
    public void whenCountSearchedWord_inContextWithSimilarSymbols_butNotWithSearchedWord_thenResultIsZero() {
        String word = "happen";

        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");

        int expectedCount = 0;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context With Upper Case then Zero Returned")
    public void whenCountSearchedWord_inContextWithUpperCase_thenZeroReturned() {
        String word = "duck";

        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "Duck tried to kill hunter.", "It was a very angry Duck.", "Was Duck.");

        int expectedCount = 0;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Analyze File With Not Existing Path then Null Pointer Exception Returned")
    public void whenAnalyzeFileWithNotExistingPath_thenNullPointerException_Returned() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String path = null;
            String word = "duck";
            fileAnalyzer.analyzeContent(word, path);
        });
    }

    @Test
    @DisplayName("when Analyze File With Not Existing Searched Word then Null Pointer Exception Returned")
    public void whenAnalyzeFileWithNotExistingSearchedWord_thenNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            String path = "src/main/resources/content.txt";
            String word = null;
            fileAnalyzer.analyzeContent(word, path);
        });
    }

    @Test
    @DisplayName("test Analyze Content when Searched Word Exist In Content")
    public void testAnalyzeContent_whenSearchedWordExistInContent() {
        String path = "src/main/resources/content.txt";
        String word = "duck";

        int expectedCount = 3;
        List<String> expectedSentences = List.of("duck tried to kill hunter.", "it was a very angry duck.", "was duck.");

        FileInformation result = fileAnalyzer.analyzeContent(path, word);

        int actualCount = result.getCount();
        List<String> actualSentences = result.getSentences();

        assertEquals(expectedCount, actualCount);

        assertEquals(expectedSentences, actualSentences);
    }

    @Test
    @DisplayName("test Analyze Content when Searched Word Is Existing In Content")
    public void testAnalyzeContent_whenSearchedWordIsNotExistInContent() {
        String path = "src/main/resources/content.txt";
        String word = "notExistWord";

        int expectedCount = 0;
        List<String> expectedSentences = Collections.emptyList();

        FileInformation result = fileAnalyzer.analyzeContent(path, word);

        int actualCount = result.getCount();
        List<String> actualSentences = result.getSentences();

        assertEquals(expectedCount, actualCount);

        assertEquals(expectedSentences, actualSentences);
    }
}
