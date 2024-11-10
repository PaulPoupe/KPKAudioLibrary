package com.example.kpkaudiolibrary.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

public class Exercise implements Iterable<Part>, Serializable {
    private final Book book;
    private final TreeMap<String, Part> parts = new TreeMap<>();
    private final int exerciseNumber;

    public Exercise(String rawExercise, int exerciseNumber, Book book) {
        this.book = book;
        this.exerciseNumber = exerciseNumber;
        addPart(rawExercise);
    }

    @NonNull
    @Override
    public Iterator<Part> iterator() {
        return parts.values().iterator();
    }

    public int getPartsCount() {
        return parts.size();
    }

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public void addPart(String rawExercisePart) {
        String partKey = getPartKey(rawExercisePart);

        if (!parts.containsKey(partKey)) {
            parts.put(partKey, new Part(partKey, rawExercisePart, book));
        }
    }

    private String getPartKey(String rawExercise) {
        String pattern = ".*_(\\d+|[a-z])\\.mp3";

        if (rawExercise.matches(pattern)) {
            return rawExercise.replaceAll(pattern, "$1");
        } else {
            return "1";
        }
    }
}