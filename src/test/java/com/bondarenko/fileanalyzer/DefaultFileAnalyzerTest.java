package com.bondarenko.fileanalyzer;

public class DefaultFileAnalyzerTest extends AbstractFileAnalyzerTest {
    @Override
    FileAnalyzer getFileAnalyzer() {
        return new DefaultFileAnalyzer();
    }
}
