package com.example.kpkaudiolibrary.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson implements Iterable<Exercise>, Serializable {
    private final TreeMap<Integer, Exercise> exercises = new TreeMap<>();
    private final int lessonNumber;
    private final String lessonName;

    public Lesson(int lessonNumber, String rawExercise) {
        putExercise(rawExercise);
        this.lessonNumber = lessonNumber;
        this.lessonName = "Затычка";
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
        if(rawExercise == null){
           throw new NullPointerException("Raw exercise is null");
        }

        int exerciseNumber = getExerciseNumber(rawExercise);

        if (!exercises.containsKey(exerciseNumber)) {
            exercises.put(exerciseNumber, new Exercise(rawExercise, exerciseNumber));
        } else {
            Objects.requireNonNull(exercises.get(exerciseNumber), "Exercise is null").addPart(rawExercise);
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
