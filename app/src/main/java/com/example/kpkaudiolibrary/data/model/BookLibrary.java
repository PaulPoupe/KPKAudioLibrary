package com.example.kpkaudiolibrary.data.model;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BookLibrary {
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<Book> workBooks = new ArrayList<>();

    public BookLibrary(Context context) {

        try {
            for (var folderName : Objects.requireNonNull(context.getAssets().list("books"))) {
                books.add(new Book(context, "books/" + folderName ));
            }

            for (var folderName : Objects.requireNonNull(context.getAssets().list("workbooks"))) {
                workBooks.add(new Book(context, "workbooks/" + folderName));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Book getBook(int bookNumber) {
        return books.get(bookNumber);
    }
    public Book getWorkBook(int workBookNumber) {
        return workBooks.get(workBookNumber);
    }

    /*
    public AssetFileDescriptor getAudioFileAfd(int bookNumber, int lessonNumber, int exerciseNumber, String partName) throws Exception {
        String audioFileAfd = "books/book1/" + books.get(bookNumber).getLesson(lessonNumber).getExercise(exerciseNumber).getAudioFileName(partName);
        return context.getAssets().openFd(audioFileAfd);
    }
     */
}
