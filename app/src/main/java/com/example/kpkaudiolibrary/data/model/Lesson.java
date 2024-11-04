package com.example.kpkaudiolibrary.data.model;

import android.content.Context;

import java.io.File;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson {
    private final TreeMap<Integer, Exercise> exercises = new TreeMap<>();

    public Lesson(String rawExercise){
        putExercise(rawExercise);
    }
    public void putExercise (String rawExercise){
        int exerciseNumber = getExerciseNumber(rawExercise);

        if (!exercises.containsKey(exerciseNumber)){
            exercises.put(exerciseNumber, new Exercise(rawExercise));
        }else{
            exercises.get(exerciseNumber).addPart(rawExercise);
        }
    }

    public Exercise getExercise(int exerciseNumber){
        return exercises.get(exerciseNumber);
    }
    private int getExerciseNumber(String rawExercise){
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
