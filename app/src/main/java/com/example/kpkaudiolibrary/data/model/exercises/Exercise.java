package com.example.kpkaudiolibrary.data.model.exercises;

import androidx.annotation.NonNull;


import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

public abstract class Exercise implements Iterable<Part>, Serializable {
    private final String path;
    private final TreeMap<String, Part> parts = new TreeMap<>();
    private final int exerciseNumber;

    protected Exercise(String rawExercise, int exerciseNumber, String path) {
        this.path = path;
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
            parts.put(partKey, new Part(partKey, rawExercisePart, path ));
        }
    }

    protected abstract String getPartKey(String rawExercise);
}
