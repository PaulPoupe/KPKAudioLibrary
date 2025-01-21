package com.paulpope.kpkaudiolibrary.data.model.lessons;

import com.paulpope.kpkaudiolibrary.data.model.books.Book;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextbookLesson extends Lesson {

    public TextbookLesson(int lessonNumber, File rawExercise, Book book) {
        super(lessonNumber, rawExercise, book);
    }

    @Override
    protected int separateExerciseNumber(File fileName) {
        Pattern pattern = Pattern.compile("^(\\d+)");
        Matcher matcher = pattern.matcher(fileName.getName());

        if (matcher.find()) {
            String numberString = matcher.group(1);
            return Integer.parseInt(numberString);
        } else {
            throw new NumberFormatException("Invalid string format");
        }
    }

}
