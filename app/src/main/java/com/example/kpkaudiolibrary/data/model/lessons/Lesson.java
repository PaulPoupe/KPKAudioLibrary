package com.example.kpkaudiolibrary.data.model.lessons;

import androidx.annotation.NonNull;

import com.example.kpkaudiolibrary.data.model.assetRepository.LessonAssetRepository;
import com.example.kpkaudiolibrary.data.model.books.Book;
import com.example.kpkaudiolibrary.data.model.exercises.Exercise;
import com.example.kpkaudiolibrary.data.model.exercises.TextbookExercise;
import com.example.kpkaudiolibrary.data.model.exercises.WorkbookExercise;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeMap;

public abstract class Lesson implements Iterable<Exercise>, Serializable {
    private final TreeMap<Integer, Exercise> exercises = new TreeMap<>();
    private final String path;
    private final Book book;
    private final int number;
    private final String name;

    protected Lesson(int number, String fileName, Book book, String path) {
        this.book = book;
        this.path = path;
        this.number = number;

        LessonAssetRepository lessonAssetRepository = new LessonAssetRepository();
        this.name = lessonAssetRepository.getLessonAsset(book.getLanguageLevel(), number).getName();

        putExercise(fileName);
    }

    @NonNull
    @Override
    public Iterator<Exercise> iterator() {
        return exercises.values().iterator();
    }


    public int getNumber() {
        return number;
    }

    protected String getPath() {
        return path;
    }

    public String getName() {
        return name;
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
