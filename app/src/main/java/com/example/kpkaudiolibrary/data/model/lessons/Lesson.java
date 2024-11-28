package com.example.kpkaudiolibrary.data.model.lessons;

import androidx.annotation.NonNull;

import com.example.kpkaudiolibrary.data.model.assetRepository.LessonAssetRepository;
import com.example.kpkaudiolibrary.data.model.books.Book;
import com.example.kpkaudiolibrary.data.model.books.BookTypes;
import com.example.kpkaudiolibrary.data.model.books.LanguageLevel;
import com.example.kpkaudiolibrary.data.model.exercises.Exercise;
import com.example.kpkaudiolibrary.data.model.exercises.TextbookExercise;
import com.example.kpkaudiolibrary.data.model.exercises.WorkbookExercise;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Lesson implements Iterable<Exercise>, Serializable {
    private final TreeMap<Integer, Exercise> exercises = new TreeMap<>();
    private final String path;
    private final Book book;
    private final int lessonNumber;
    private final String lessonName;

    protected Lesson(int lessonNumber, String rawExercise, Book book, String path) {
        this.book = book;
        this.path = path;
        this.lessonNumber = lessonNumber;

        LessonAssetRepository lessonAssetRepository = new LessonAssetRepository();
        this.lessonName = lessonAssetRepository.getLessonAsset(book.getLanguageLevel(), lessonNumber).getName();

        putExercise(rawExercise);
    }

    @NonNull
    @Override
    public Iterator<Exercise> iterator() {
        return exercises.values().iterator();
    }

    public Exercise getExercise(int exerciseNumber) {
        return exercises.get(exerciseNumber);
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void putExercise(String rawExercise) throws NullPointerException {
        if (rawExercise == null) {
            throw new NullPointerException("Raw exercise is null");
        }

        int exerciseNumber = separateExerciseNumber(rawExercise);

        if (!exercises.containsKey(exerciseNumber)) {
            switch (book.getBookType()) {
                case Textbook:
                    exercises.put(exerciseNumber, new TextbookExercise(rawExercise, exerciseNumber, path));
                    break;
                case Workbook:
                    exercises.put(exerciseNumber, new WorkbookExercise(rawExercise, exerciseNumber, path));
                    break;
            }
        } else {
            Objects.requireNonNull(exercises.get(exerciseNumber), "Exercise is null").addPart(rawExercise);
        }
    }

    protected abstract int separateExerciseNumber(String rawExercise);
}
