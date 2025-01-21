package com.paulpope.kpkaudiolibrary.data.model.books;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.paulpope.kpkaudiolibrary.data.model.lessons.Lesson;
import com.paulpope.kpkaudiolibrary.data.model.assetRepository.BookAssetRepository;
import com.paulpope.kpkaudiolibrary.data.model.lessons.TextbookLesson;
import com.paulpope.kpkaudiolibrary.data.model.lessons.WorkbookLesson;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeMap;

public abstract class Book implements Serializable {
    private final TreeMap<Integer, Lesson> lessons = new TreeMap<>();
    private final LanguageLevel languageLevel;
    private final BookTypes bookType;
    private final int iconId;

    public Book(Context context, String folderName, BookTypes bookType, File folder) throws IOException {
        this.bookType = bookType;
        languageLevel = LanguageLevel.valueOf(folderName);

        BookAssetRepository assetRepository = new BookAssetRepository();
        iconId = assetRepository.getBookAsset(languageLevel, bookType).getIconId();

        File[] files = folder.listFiles();
        putLessons(files);
    }

    @NonNull
    public Collection<Lesson> getLessons() {
        return lessons.values();
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
    private void putLessons(File[] rawExercises) {
        for (var rawExercise : Objects.requireNonNull(rawExercises, "Exercises is null")) {
            int lessonNumber = separateLessonNumber(rawExercise);
            if (!lessons.containsKey(lessonNumber)) {

                switch (bookType) {
                    case Textbook:
                        lessons.put(lessonNumber, new TextbookLesson(lessonNumber, rawExercise, this));
                        break;
                    case Workbook:
                        lessons.put(lessonNumber, new WorkbookLesson(lessonNumber, rawExercise, this));
                        break;
                }
            }
            Objects.requireNonNull(lessons.get(lessonNumber)).putExercise(rawExercise);
        }
    }

    protected abstract int separateLessonNumber(File rawExercise);
}
