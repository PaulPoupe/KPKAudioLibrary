package com.paulpope.kpkaudiolibrary.data.model.exercises;


import java.io.File;
import java.io.Serializable;

public class Part implements Serializable {
    private final String name;
    private final String audioFilePath;

    public Part(String partName, File fileName) {
        this.name = partName;
        audioFilePath = fileName.getPath();
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public String getName() {
        return name;
    }
}
