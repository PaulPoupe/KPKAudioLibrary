package com.example.kpkaudiolibrary.data.model;

import android.content.Context;
import android.content.res.AssetManager;
import androidx.annotation.NonNull;

import com.example.kpkaudiolibrary.data.model.assetRepository.BookAssetRepository;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book implements Iterable<Lesson>, Serializable {
    private final TreeMap<Integer, Lesson> lessons = new TreeMap<>();

    private final LanguageLevel languageLevel;
    private final BookTypes bookType;
    private final int iconId;

    public Book(Context context, String folderName, BookTypes bookType, String directoryPath) throws IOException {
        BookAssetRepository assetRepository = new BookAssetRepository();

        AssetManager assetManager = context.getAssets();
        String[] rawExercises = assetManager.list(directoryPath);

        this.bookType = bookType;
        languageLevel = LanguageLevel.valueOf(folderName);
        iconId = assetRepository.getBookAsset(languageLevel, bookType).getIconId();

        putLessons(rawExercises);
    }

    @NonNull
    @Override
    public Iterator<Lesson> iterator() {
        return lessons.values().iterator();
    }

    public LanguageLevel getLanguageLevel(){
        return languageLevel;
    }

    public BookTypes getBookType(){
        return bookType;
    }

    public int getIconId(){
        return iconId;
    }

    public Lesson getLesson(Integer lessonNumber) {
        return lessons.get(lessonNumber);
    }

    private void putLessons(String[] rawExercises) {
        for (var rawExercise : Objects.requireNonNull(rawExercises, "Exercises is null")) {
            int lessonNumber = separateLessonNumber(rawExercise);

            if (!lessons.containsKey(lessonNumber)) {
                lessons.put(lessonNumber, new Lesson(lessonNumber, rawExercise, languageLevel));
            }
            Objects.requireNonNull(lessons.get(lessonNumber)).putExercise(rawExercise);
        }
    }

    private int separateLessonNumber(String rawExercise) {
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
