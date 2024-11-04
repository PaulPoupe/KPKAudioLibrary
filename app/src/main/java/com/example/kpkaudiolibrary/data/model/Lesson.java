package com.example.kpkaudiolibrary.data.model;

import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson implements Iterable<Exercise> {
    private final TreeMap<Integer, Exercise> exercises = new TreeMap<>();
    private final int lessonNumber;

    public Lesson(int lessonNumber, String rawExercise) {
        putExercise(rawExercise);
        this.lessonNumber = lessonNumber;
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

    public void putExercise(String rawExercise) {
        int exerciseNumber = getExerciseNumber(rawExercise);

        if (!exercises.containsKey(exerciseNumber)) {
            exercises.put(exerciseNumber, new Exercise(rawExercise));
        } else {
            exercises.get(exerciseNumber).addPart(rawExercise);
        }
    }

    private int getExerciseNumber(String rawExercise) {
        Pattern pattern = Pattern.compile("cwiczenie(\\d+)");
        Matcher matcher = pattern.matcher(rawExercise);

        if (matcher.find()) {
            String numberString = matcher.group(1);
            return Integer.parseInt(numberString);
        } else {
            throw new NumberFormatException("Invalid string format");
        }
    }
}
