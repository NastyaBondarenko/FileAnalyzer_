package com.bondarenko.fileanalyzer;

import com.bondarenko.fileanalyzer.streamfileanalyzer.StreamFileAnalyzer;

public class StreamFileAnalyzerTest extends AbstractFileAnalyzerTest {
    @Override
    FileAnalyzer getFileAnalyzer() {
        return new StreamFileAnalyzer();
    }
}
