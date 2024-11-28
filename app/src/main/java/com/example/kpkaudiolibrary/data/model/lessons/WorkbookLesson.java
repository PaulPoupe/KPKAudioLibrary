package com.example.kpkaudiolibrary.data.model.lessons;

import com.example.kpkaudiolibrary.data.model.books.Book;
import com.example.kpkaudiolibrary.data.model.books.LanguageLevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkbookLesson extends Lesson{
    public WorkbookLesson(int lessonNumber, String rawExercise, Book book, String path) {
        super(lessonNumber, rawExercise, book, path);
    }

    @Override
    protected int separateExerciseNumber(String rawExercise) {
        Pattern pattern = Pattern.compile("cwiczenie(\\d+)");
        Matcher matcher = pattern.matcher(rawExercise);

        if (matcher.find()) {
            String numberString = matcher.group(1);
            return Integer.parseInt(numberString);
        } else {
            throw new NumberFormatException("Invalid string format");
        }
    }
}
