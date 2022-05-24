package com.bondarenko.fileinformation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FileInformation {
    private int count;
    private String word;
    private List<String> sentences;

    public FileInformation(String word, List<String> sentences, int count) {
        this.word = word;
        this.sentences = sentences;
        this.count = count;
    }
}