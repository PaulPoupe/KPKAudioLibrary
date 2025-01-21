package com.paulpope.kpkaudiolibrary.data.model.exercises;

import com.paulpope.kpkaudiolibrary.data.model.assetRepository.NameExtractor;

import java.io.File;

public class TextbookExercise extends Exercise {
    private final String name;

    public TextbookExercise(File fileName, int number) {
        super(fileName, number);
        name = extractName(fileName);
    }

    @Override
    protected String getPartKey(File fileName) {
        return "1";
    }

    public String getName() {
        return name;
    }

    public Part getPart() {
       return parts.get("1");
    }

    private String extractName(File fileName) {
        try {
            String fullName = NameExtractor.extractName(fileName);
            String delimiter = ", ";
            int delimiterIndex = fullName.indexOf(delimiter);

            return fullName.substring(delimiterIndex + delimiter.length());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при извлечении имени из файла:" + "rawExercise");
        }
    }
}
