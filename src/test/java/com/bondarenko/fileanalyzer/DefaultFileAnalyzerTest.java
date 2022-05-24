package com.bondarenko.fileanalyzer;

import com.bondarenko.fileanalyzer.defaultfileanalyzer.DefaultFileAnalyzer;

public class DefaultFileAnalyzerTest extends AbstractFileAnalyzerTest {
    @Override
    FileAnalyzer getFileAnalyzer() {
        return new DefaultFileAnalyzer();
    }
}
