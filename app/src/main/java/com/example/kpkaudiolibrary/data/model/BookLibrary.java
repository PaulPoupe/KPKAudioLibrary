package com.example.kpkaudiolibrary.data.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import java.io.IOException;

public class BookLibrary {
    private final Book book1;
    private final Context context;

    public BookLibrary(Context context) throws IOException {
        this.context = context;
        book1 = new Book(context, "books/book1");
    }

    public AssetFileDescriptor getAudioFileAfd(int lessonNumber, int exerciseNumber, String partName) throws Exception {
        String audioFileAfd = "books/book1/" + book1.getLesson(lessonNumber).getExercise(exerciseNumber).getAudioFileName(partName);
        return context.getAssets().openFd(audioFileAfd);
    }
}
