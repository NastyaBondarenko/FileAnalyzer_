package com.bondarenko.fileanalyzer;

import com.bondarenko.fileinformation.FileInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFileAnalyzerTest {
    FileAnalyzer fileAnalyzer = getFileAnalyzer();

    abstract FileAnalyzer getFileAnalyzer();

    String content = "Listen!One day!What happened?A duck tried to kill a hunter.It was a very angry duck.It was a duck.";
    String path = "src/test/resources/content.txt";

    @Test
    @DisplayName("test Read Content")
    public void testReadContent() {
        String expectedResult = content;
        String actualResult = fileAnalyzer.readContent(path);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test Split Into Sentences")
    public void testSplitIntoSentences_WithValidSeparators() {
        List<String> expectedResult = List.of("Listen!", "One day!", "What happened?", "A duck tried to kill a hunter.", "It was a very angry duck.", "It was a duck.");
        List<String> actualResult = fileAnalyzer.splitIntoSentences(content);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test Split Into Sentences With Invalid Separators")
    public void testSplitIntoSentences_WithInvalidSeparators() {
        String content = "Listen<>One day$ What happened;duck tried to kill hunter;It was a very angry duck#It was a duck@";

        List<String> expectedResult = List.of(content);
        List<String> actualResult = fileAnalyzer.splitIntoSentences(content);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test Split Into Sentences Without Separators")
    public void testSplitIntoSentences_WithoutSeparators() {
        String content = "Just content";

        List<String> expectedResult = List.of(content);
        List<String> actualResult = fileAnalyzer.splitIntoSentences(content);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("when Search Sentences By Searched Word than Sentences With This Word is Returned")
    public void whenSearchSentences_BySearchedWord_thanSentencesWithThisWord_IsReturned() {
        String word = "duck";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "It was a duck.");

        List<String> expectedSentences = List.of("duck tried to kill hunter.", "it was a very angry duck.", "it was a duck.");
        List<String> actualSentences = fileAnalyzer.filter(initialSentences, word);

        assertEquals(expectedSentences, actualSentences);
    }

    @Test
    @DisplayName("when Search Sentences By Search Word in Empty List than Empty List Returned")
    public void whenSearchSentencesBySearchWord_inEmptyList_thanEmptyListReturned() {
        String word = "duck";

        List<String> initialSentences = Collections.emptyList();
        List<String> actualSentences = fileAnalyzer.filter(initialSentences, word);

        assertEquals(initialSentences, actualSentences);
    }

    @Test
    @DisplayName("when Search Sentences Without Search Word than Empty List Returned")
    public void whenSearchSentences_WithoutSearchWord_thanEmptyListReturned() {
        String word = "notExistingWord";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");

        List<String> expectedSentences = Collections.emptyList();
        List<String> actualSentences = fileAnalyzer.filter(initialSentences, word);

        assertEquals(expectedSentences, actualSentences);
    }

    @Test
    @DisplayName("when Count Search Word in Context With One Searched Word than One Returned")
    public void whenCountSearchWord_inContextWithOneSearchedWord_thanOneReturned() {
        String word = "duck";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.");

        int expectedCount = 1;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context Without Searched Word than Zero Returned")
    public void whenCountSearchedWord_inContextWithoutSearchedWord_thanZeroReturned() {
        String word = "duck";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?");

        int expectedCount = 0;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context With More Then One Searched Word than Appropriate Count Returned")
    public void whenCountSearchedWord_inContextWithMoreThenOneSearchedWord_thanAppropriateCountReturned() {
        String word = "duck";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "duck tried to kill hunter.", "It was a very angry duck.", "Was duck.");

        int expectedCount = 3;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Searched Word in Context With Upper Case than Zero Returned")
    public void whenCountSearchedWord_inContextWithUpperCase_thanZeroReturned() {
        String word = "duck";
        List<String> initialSentences = List.of("Listen", "One day!", "What happened?", "Duck tried to kill hunter.", "It was a very angry Duck.", "Was Duck.");

        int expectedCount = 0;
        int actualCount = fileAnalyzer.countWords(initialSentences, word);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Analyze File With Not Existing Path than Null Pointer Exception Returned")
    public void whenAnalyzeFileWithNotExistingPath_thanNullPointerException_Returned() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String path = null;
            String word = "duck";
            fileAnalyzer.analyze(word, path);
        });
    }

    @Test
    @DisplayName("when Analyze File With Not Existing Searched Word than Null Pointer Exception Returned")
    public void whenAnalyzeFileWithNotExistingSearchedWord_thanNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            String word = null;
            fileAnalyzer.analyze(word, path);
        });
    }

    @Test
    @DisplayName("test Analyze Content when Searched Word Exist In Content")
    public void testAnalyzeContent_whenSearchedWordExistInContent() {
        String word = "duck";
        FileInformation result = fileAnalyzer.analyze(path, word);

        int expectedCount = 3;
        int actualCount = result.getCount();

        List<String> expectedSentences = List.of("a duck tried to kill a hunter.", "it was a very angry duck.", "it was a duck.");
        List<String> actualSentences = result.getSentences();

        assertEquals(expectedCount, actualCount);
        assertEquals(expectedSentences, actualSentences);
    }

    @Test
    @DisplayName("test Analyze Content when Searched Word Is Not Exist In Content")
    public void testAnalyzeContent_whenSearchedWord_IsNotExistInContent() {
        String word = "notExistWord";
        FileInformation result = fileAnalyzer.analyze(path, word);

        int actualCount = result.getCount();
        int expectedCount = 0;

        List<String> actualSentences = result.getSentences();
        List<String> expectedSentences = Collections.emptyList();

        assertEquals(expectedCount, actualCount);
        assertEquals(expectedSentences, actualSentences);
    }
}
