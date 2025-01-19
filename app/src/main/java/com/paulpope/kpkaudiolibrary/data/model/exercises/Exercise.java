package com.paulpope.kpkaudiolibrary.data.model.exercises;

import androidx.annotation.NonNull;


import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

public abstract class Exercise implements Iterable<Part>, Serializable {
    protected final TreeMap<String, Part> parts = new TreeMap<>();
    private final String path;
    private final int number;

    protected Exercise(String rawExercise, int number, String path) {
        this.path = path;
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public void addPart(String rawExercisePart) {
        String partKey = getPartKey(rawExercisePart);

        if (!parts.containsKey(partKey)) {
            parts.put(partKey, new Part(partKey, rawExercisePart, path ));
        }
    }

    protected abstract String getPartKey(String rawExercise);
}
