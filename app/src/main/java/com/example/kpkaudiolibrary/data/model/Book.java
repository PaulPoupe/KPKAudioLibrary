package com.example.kpkaudiolibrary.data.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book {
    private final TreeMap<Integer, Lesson> lessons = new TreeMap<>();

    public Book(Context context, String directoryPath) throws IOException {
        AssetManager assetManager = context.getAssets();
        String[] rawExercises = assetManager.list(directoryPath);

        for (var rawExercise : rawExercises) {
            int lessonNumber = getLessonNumber(rawExercise);
            if (!lessons.containsKey(lessonNumber)) {
                lessons.put(lessonNumber, new Lesson(rawExercise));
            } else {
                lessons.get(lessonNumber).putExercise(rawExercise);
            }
        }
    }

    public Lesson getLesson(Integer lessonNumber) {
        return lessons.get(lessonNumber);
    }

    private int getLessonNumber(String rawExercise) {
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
