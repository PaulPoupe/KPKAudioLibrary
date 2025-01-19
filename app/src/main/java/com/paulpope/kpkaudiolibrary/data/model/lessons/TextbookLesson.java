package com.paulpope.kpkaudiolibrary.data.model.lessons;

import com.paulpope.kpkaudiolibrary.data.model.books.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextbookLesson extends Lesson {

    public TextbookLesson(int lessonNumber, String rawExercise, Book book, String path) {
        super(lessonNumber, rawExercise, book, path);
    }

    @Override
    protected int separateExerciseNumber(String fileName) {
        Pattern pattern = Pattern.compile("^(\\d+)");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            String numberString = matcher.group(1);
            return Integer.parseInt(numberString);
        } else {
            throw new NumberFormatException("Invalid string format");
        }
    }

}
