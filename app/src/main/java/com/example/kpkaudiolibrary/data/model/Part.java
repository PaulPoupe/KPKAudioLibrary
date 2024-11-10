package com.example.kpkaudiolibrary.data.model;


import java.io.Serializable;

public class Part implements Serializable {
    private final String name;
    private final String audioFilePath;

    public Part(String partName, String fileName, Book book) {
        this.name = partName;


        String bookType = null;
        switch (book.getBookType()){
            case Book:
                bookType = "books/";
                break;
            case Workbook:
                bookType = "workbooks/";
                break;
        }

        String languageLevel = null;
        switch (book.getLanguageLevel()){
            case A1:
                languageLevel = "A1/";
                break;
            case A2:
                languageLevel = "A2/";
        }


        this.audioFilePath = bookType + languageLevel + fileName;
    }

    public String getAudioFilePath () {
        return audioFilePath;
    }

    public String getName() {
        return name;
    }
}
