package com.paulpope.kpkaudiolibrary.data.model.exercises;

import java.io.Serializable;

public class WorkbookExercise extends Exercise implements Iterable<Part>, Serializable {
    public WorkbookExercise(String fileName, int number, String path) {
        super(fileName, number, path);
    }

    @Override
    protected String getPartKey(String fileName) {
        String pattern = ".*_(\\d+|[a-z])\\.mp3";

        if (fileName.matches(pattern)) {
            return fileName.replaceAll(pattern, "$1");
        } else {
            return "1";
        }
    }
}