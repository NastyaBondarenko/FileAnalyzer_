package com.bondarenko.fileanalyzer;

public class StreamFileAnalyzerTest extends AbstractFileAnalyzerTest {
    @Override
    FileAnalyzer getFileAnalyzer() {
        return new StreamFileAnalyzer();
    }
}
