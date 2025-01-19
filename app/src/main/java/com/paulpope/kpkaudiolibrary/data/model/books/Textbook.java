package com.paulpope.kpkaudiolibrary.data.model.books;

import android.content.Context;

import com.paulpope.kpkaudiolibrary.data.model.assetRepository.NameExtractor;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Textbook extends Book{

    public Textbook(Context context, String folderName, String directoryPath) throws IOException {
        super(context, folderName, BookTypes.Textbook, directoryPath);
    }

    @Override
    protected int separateLessonNumber(String rawExercise) {
        try {
            String fullName = NameExtractor.extractName(getPath() + "/" + rawExercise);

            Pattern pattern = Pattern.compile("Lekcja\\s(\\d+)");
            Matcher matcher = pattern.matcher(fullName);

            if (matcher.find()) {
                String numberString = matcher.group(1);
                return Integer.parseInt(numberString);
            } else {
                throw new IllegalArgumentException("Invalid string format: " + rawExercise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
