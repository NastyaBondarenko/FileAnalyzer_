package com.bondarenko.fileinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class FileInformation {
    private String word;
    private List<String> sentences;
    private int count;
}
