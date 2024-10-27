package com.example.kpkaudiolibrary.data.repository;


import com.example.kpkaudiolibrary.data.model.Lesson;

import java.io.File;
import java.util.Objects;
import java.util.TreeMap;

public class AudioLibrary {
    public final TreeMap<Integer, Lesson> lessons = new TreeMap<>();

    public void sort() throws Exception {
        File folder = new File("resources/AudioFiles");

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            int lessonNumber = extractLNumber(file.getName());

            if (!lessons.containsKey(lessonNumber)) {
                lessons.put(lessonNumber, new Lesson());
            }
            lessons.get(lessonNumber).addExercise(file);
        }
    }

    private int extractLNumber(String fileName) {
        String keyword = "L";
        int index = fileName.indexOf(keyword);
        if (index != -1) {
            // Извлекаем подстроку после 'L' (например, "06") и преобразуем в int
            String number = fileName.substring(index + 1, index + 3); // Предполагается, что число состоит из 2 цифр
            return Integer.parseInt(number);
        }
        return -1; // Возвращаем -1, если 'L' не найдено
    }
}