package com.example.kpkaudiolibrary.data.model;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class BookLibrary implements Iterable<Book> {

    private final static String BOOKS_FOLDER = "books";
    private final static String WORKBOOKS_FOLDER = "workbooks";
    private final ArrayList<Book> books = new ArrayList<>();

    @NonNull
    @Override
    public Iterator<Book> iterator() {
        return books.iterator();
    }

    public BookLibrary(Context context) {
        try {
            for (var folderName : Objects.requireNonNull(context.getAssets().list(BOOKS_FOLDER))) {
                books.add(new Book(context, folderName, BookTypes.Book,BOOKS_FOLDER + '/' + folderName ));
            }

            for (var folderName : Objects.requireNonNull(context.getAssets().list(WORKBOOKS_FOLDER))) {
                books.add(new Book(context, folderName, BookTypes.Workbook, WORKBOOKS_FOLDER+ '/' + folderName));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
