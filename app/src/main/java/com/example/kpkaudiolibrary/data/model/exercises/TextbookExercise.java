package com.example.kpkaudiolibrary.data.model.exercises;

import com.example.kpkaudiolibrary.data.model.books.Book;

public class TextbookExercise extends Exercise {

    public TextbookExercise(String rawExercise, int exerciseNumber, String path) {
        super(rawExercise, exerciseNumber, path);
    }

    @Override
    protected String getPartKey(String rawExercise) {
        throw new RuntimeException();
    }
}
