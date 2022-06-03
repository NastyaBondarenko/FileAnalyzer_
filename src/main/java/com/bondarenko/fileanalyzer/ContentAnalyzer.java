package com.bondarenko.fileanalyzer;

import com.bondarenko.fileinformation.ContentInformation;

public interface ContentAnalyzer {

    ContentInformation analyze(String path, String word);
}
