package com.paulpope.kpkaudiolibrary.data.model.books;

import android.content.Context;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Workbook extends Book {
    public Workbook(Context context, String folderName, String directoryPath) throws IOException {
        super(context, folderName, BookTypes.Workbook, directoryPath);
    }

    @Override
    protected int separateLessonNumber(String rawExercise) {
        Pattern pattern = Pattern.compile("l(\\d+)");
        Matcher matcher = pattern.matcher(rawExercise);

        if (matcher.find()) {
            String numberString = matcher.group(1);
            return Integer.parseInt(numberString);
        } else {
            throw new NumberFormatException("Invalid string format");
        }
    }
}
