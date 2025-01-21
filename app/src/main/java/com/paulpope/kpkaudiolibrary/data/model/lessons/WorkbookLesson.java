package com.paulpope.kpkaudiolibrary.data.model.lessons;

import com.paulpope.kpkaudiolibrary.data.model.books.Book;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkbookLesson extends Lesson{
    public WorkbookLesson(int lessonNumber, File rawExercise, Book book) {
        super(lessonNumber, rawExercise, book);
    }

    @Override
    protected int separateExerciseNumber(File rawExercise) {
        Pattern pattern = Pattern.compile("cwiczenie(\\d+)");
        Matcher matcher = pattern.matcher(rawExercise.getName());

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new NumberFormatException("Invalid string format");
        }
    }
}
