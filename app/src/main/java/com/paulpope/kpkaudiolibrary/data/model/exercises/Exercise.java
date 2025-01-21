package com.paulpope.kpkaudiolibrary.data.model.exercises;

import androidx.annotation.NonNull;


import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;

public abstract class Exercise implements Serializable {
    protected final TreeMap<String, Part> parts = new TreeMap<>();
    private final int number;

    protected Exercise(File rawExercise, int number) {
        this.number = number;
        addPart(rawExercise);
    }

    @NonNull
    public Collection<Part> getParts() {
        return parts.values();
    }

    public int getPartsCount() {
        return parts.size();
    }

    public int getNumber() {
        return number;
    }

    public void addPart(File rawExercisePart) {
        String partKey = getPartKey(rawExercisePart);

        if (!parts.containsKey(partKey)) {
            parts.put(partKey, new Part(partKey, rawExercisePart));
        }
    }

    protected abstract String getPartKey(File rawExercise);
}
