package com.paulpope.kpkaudiolibrary.data.model.exercises;

import java.io.File;
import java.io.Serializable;

public class WorkbookExercise extends Exercise implements Serializable {
    public WorkbookExercise(File fileName, int number) {
        super(fileName, number);
    }

    @Override
    protected String getPartKey(File fileName) {
        String pattern = ".*_(\\d+|[a-z])\\.mp3";

        if (fileName.getName().matches(pattern)) {
            return fileName.getName().replaceAll(pattern, "$1");
        } else {
            return "1";
        }
    }
}