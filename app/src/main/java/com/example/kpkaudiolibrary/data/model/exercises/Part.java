package com.example.kpkaudiolibrary.data.model.exercises;


import com.example.kpkaudiolibrary.data.model.books.Book;

import java.io.Serializable;

public class Part implements Serializable {
    private final String name;
    private final String audioFilePath;

    public Part(String partName, String fileName, String path) {
        this.name = partName;
        audioFilePath = path + "/" + fileName;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public String getName() {
        return name;
    }
}
