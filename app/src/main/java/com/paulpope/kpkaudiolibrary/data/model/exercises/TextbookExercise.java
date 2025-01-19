package com.paulpope.kpkaudiolibrary.data.model.exercises;

import com.paulpope.kpkaudiolibrary.data.model.assetRepository.NameExtractor;

public class TextbookExercise extends Exercise {
    private final String name;

    public TextbookExercise(String fileName, int number, String path) {
        super(fileName, number, path);
        name = extractName(fileName, path);
    }

    @Override
    protected String getPartKey(String fileName) {
        return "1";
    }

    public String getName() {
        return name;
    }

    public Part getPart() {
       return parts.get("1");
    }

    private String extractName(String fileName, String path) {
        try {
            String fullName = NameExtractor.extractName(path + "/" + fileName);
            String delimiter = ", ";
            int delimiterIndex = fullName.indexOf(delimiter);

            return fullName.substring(delimiterIndex + delimiter.length());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при извлечении имени из файла:" + "rawExercise");
        }
    }
}
