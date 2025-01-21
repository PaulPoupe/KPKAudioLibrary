package com.paulpope.kpkaudiolibrary.data.model.lessons;

import com.paulpope.kpkaudiolibrary.data.model.assetRepository.LessonAssetRepository;
import com.paulpope.kpkaudiolibrary.data.model.books.Book;
import com.paulpope.kpkaudiolibrary.data.model.exercises.Exercise;
import com.paulpope.kpkaudiolibrary.data.model.exercises.TextbookExercise;
import com.paulpope.kpkaudiolibrary.data.model.exercises.WorkbookExercise;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeMap;

public abstract class Lesson implements Serializable {
    private final TreeMap<Integer, Exercise> exercises = new TreeMap<>();
    private final Book book;
    private final int number;
    private final String name;

    protected Lesson(int number, File fileName, Book book) {
        this.book = book;
        this.number = number;

        LessonAssetRepository lessonAssetRepository = new LessonAssetRepository();
        this.name = lessonAssetRepository.getLessonAsset(book.getLanguageLevel(), number).getName();

        putExercise(fileName);
    }

    public Collection<Exercise> getExercises() {
        return exercises.values();
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void putExercise(File rawExercise) throws NullPointerException {
        if (rawExercise == null) {
            throw new NullPointerException("Raw exercise is null");
        }


        int exerciseNumber = separateExerciseNumber(rawExercise);

        if (!exercises.containsKey(exerciseNumber)) {
            switch (book.getBookType()) {
                case Textbook:
                    exercises.put(exerciseNumber, new TextbookExercise(rawExercise, exerciseNumber));
                    break;
                case Workbook:
                    exercises.put(exerciseNumber, new WorkbookExercise(rawExercise, exerciseNumber));
                    break;
            }
        } else {
            Objects.requireNonNull(exercises.get(exerciseNumber), "Exercise is null").addPart(rawExercise);
        }
    }

    protected abstract int separateExerciseNumber(File rawExercise);
}
