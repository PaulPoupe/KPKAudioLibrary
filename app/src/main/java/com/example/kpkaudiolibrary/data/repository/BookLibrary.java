package com.example.kpkaudiolibrary.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.kpkaudiolibrary.data.model.books.Book;
import com.example.kpkaudiolibrary.data.model.books.BookTypes;
import com.example.kpkaudiolibrary.data.model.books.Textbook;
import com.example.kpkaudiolibrary.data.model.books.Workbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class BookLibrary implements Iterable<Book> {

    private final static String BOOKS_FOLDER = "textbooks";
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
                books.add(new Textbook(context, folderName, BOOKS_FOLDER + '/' + folderName));
            }

            for (var folderName : Objects.requireNonNull(context.getAssets().list(WORKBOOKS_FOLDER))) {
                books.add(new Workbook(context, folderName, WORKBOOKS_FOLDER + '/' + folderName));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
