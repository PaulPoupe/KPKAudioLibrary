package com.example.kpkaudiolibrary.data.model.books;

import android.content.Context;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Textbook extends Book{

    public Textbook(Context context, String folderName, String directoryPath) throws IOException {
        super(context, folderName, BookTypes.Textbook, directoryPath);
    }

    @Override
    protected int separateLessonNumber(String rawExercise) {
        return 0;
    }
}
