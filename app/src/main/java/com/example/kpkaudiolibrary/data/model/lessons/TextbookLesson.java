package com.example.kpkaudiolibrary.data.model.lessons;

import com.example.kpkaudiolibrary.data.model.books.Book;

public class TextbookLesson extends Lesson {

    public TextbookLesson(int lessonNumber, String rawExercise, Book book, String path) {
        super(lessonNumber, rawExercise, book, path);
    }

    @Override
    protected int separateExerciseNumber(String rawExercise) {
        return 0;
    }
}
