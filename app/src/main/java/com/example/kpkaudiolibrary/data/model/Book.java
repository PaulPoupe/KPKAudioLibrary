package com.example.kpkaudiolibrary.data.model;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.example.kpkaudiolibrary.data.model.assetRepository.BookAssetRepository;
import com.example.kpkaudiolibrary.data.model.assetRepository.NameExtractor;

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

    public Book(Context context, String folderName, BookTypes bookType, String directoryPath) throws Exception {
        BookAssetRepository assetRepository = new BookAssetRepository();

        AssetManager assetManager = context.getAssets();
        String[] rawExercises = assetManager.list(directoryPath);

        this.bookType = bookType;
        languageLevel = LanguageLevel.valueOf(folderName);
        iconId = assetRepository.getBookAsset(languageLevel, bookType).getIconId();

        putLessons(rawExercises, directoryPath, context);
    }

    @NonNull
    @Override
    public Iterator<Lesson> iterator() {
        return lessons.values().iterator();
    }

    public LanguageLevel getLanguageLevel() {
        return languageLevel;
    }

    public BookTypes getBookType() {
        return bookType;
    }

    public int getIconId() {
        return iconId;
    }

    public Lesson getLesson(Integer lessonNumber) {
        return lessons.get(lessonNumber);
    }

    private void putLessons(String[] rawExercises, String directoryPath, Context context) throws Exception {
        String[] updatedExercises;
        if (bookType == BookTypes.Book){
            for(int i = 0; i < rawExercises.length; i++){
                rawExercises[i] = NameExtractor.extractName( context,directoryPath + '/' + rawExercises[i]);
            }
            updatedExercises = NameExtractor.updateExerciseNumbers(rawExercises);
        } else {
            updatedExercises = rawExercises;
        }

        for (var rawExercise : Objects.requireNonNull(updatedExercises, "Exercises is null")) {
            int lessonNumber;
            if(bookType == BookTypes.Book){
                lessonNumber = separateLessonNumberBook(rawExercise);
            }else {
                lessonNumber = separateLessonNumber(rawExercise);
            }

            if (!lessons.containsKey(lessonNumber)) {
                lessons.put(lessonNumber, new Lesson(lessonNumber, rawExercise, this));
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

    private int separateLessonNumberBook(String rawExercise) {
        Pattern pattern = Pattern.compile("Lekcja\\s(\\d+)");
        Matcher matcher = pattern.matcher(rawExercise);

        if (matcher.find()) {
            String numberString = matcher.group(1);
            return Integer.parseInt(numberString);
        } else {
            throw new NumberFormatException("Invalid string format: Lekcja number not found");
        }
    }

}
