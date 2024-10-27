package com.example.kpkaudiolibrary.data.model;

import java.io.File;
import java.util.TreeMap;

public class Lesson {
    public final TreeMap<Integer, Exercise> exercises = new TreeMap<>();

    public void addExercise(File rawExerciseFile) {
        int exercisesNumber = extractExerciseNumber(rawExerciseFile.getName());

        if (!exercises.containsKey(exercisesNumber)) {
            exercises.put(exercisesNumber, new Exercise());
        }
        exercises.get(exercisesNumber).addExerciseParts(rawExerciseFile);
    }

    private int extractExerciseNumber(String fileName) {
        String keyword = "cwiczenie";
        int index = fileName.toLowerCase().indexOf(keyword); // Поиск слова 'cwiczenie', с игнорированием регистра

        if (index != -1) {
            // Извлекаем подстроку сразу после 'cwiczenie'
            StringBuilder number = new StringBuilder();
            int i = index + keyword.length();

            // Пока символ является цифрой, добавляем его к нашему числу
            while (i < fileName.length() && Character.isDigit(fileName.charAt(i))) {
                number.append(fileName.charAt(i));
                i++;
            }

            // Если нашли цифры, преобразуем их в число
            if (number.length() > 0) {
                return Integer.parseInt(number.toString());
            }
        }

        return -1; // Возвращаем -1, если слово 'cwiczenie' не найдено или нет чисел после него
    }
}
