package com.example.kpkaudiolibrary.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

public class Exercise implements Iterable<String>, Serializable {
    private final TreeMap<String, String> parts = new TreeMap<>();
    private final int exerciseNumber;

    public Exercise(String rawExercise, int exerciseNumber) {
        addPart(rawExercise);
        this.exerciseNumber = exerciseNumber;
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return parts.values().iterator();
    }

    public int getPartsCount() {
        return parts.size();
    }

    public String getAudioFileName(String partName) throws Exception {
        if (!parts.containsKey(partName)) {
            throw new Exception("Not have such part");
        }
        return parts.get(partName);
    }

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public void addPart(String rawExercisePart) {
        String partKey = getPartKey(rawExercisePart);

        if (!parts.containsKey(partKey)) {
            parts.put(getPartKey(rawExercisePart), rawExercisePart);
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