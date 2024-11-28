package com.example.kpkaudiolibrary.data.model.exercises;

import androidx.annotation.NonNull;

import com.example.kpkaudiolibrary.data.model.books.Book;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

public class WorkbookExercise extends Exercise implements Iterable<Part>, Serializable {
    public WorkbookExercise(String rawExercise, int exerciseNumber, String path) {
        super(rawExercise, exerciseNumber, path);
    }

    @Override
    protected String getPartKey(String rawExercise) {
        String pattern = ".*_(\\d+|[a-z])\\.mp3";

        if (rawExercise.matches(pattern)) {
            return rawExercise.replaceAll(pattern, "$1");
        } else {
            return "1";
        }
    }
}