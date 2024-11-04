package com.example.kpkaudiolibrary.data.model;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;

public class BookLibrary {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Book> workBooks = new ArrayList<>();
    private final Context context;

    public BookLibrary(Context context) throws IOException {
        this.context = context;

        for (var folderName : context.getAssets().list("books")) {
            books.add(new Book(context, "books/" + folderName));
        }
        ;
    }

    public Book getBook(int bookNumber) {
        return books.get(bookNumber);
    }

    /*
    public AssetFileDescriptor getAudioFileAfd(int bookNumber, int lessonNumber, int exerciseNumber, String partName) throws Exception {
        String audioFileAfd = "books/book1/" + books.get(bookNumber).getLesson(lessonNumber).getExercise(exerciseNumber).getAudioFileName(partName);
        return context.getAssets().openFd(audioFileAfd);
    }
     */
}
