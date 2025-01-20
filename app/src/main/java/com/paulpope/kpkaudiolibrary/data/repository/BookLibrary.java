package com.paulpope.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.util.Log;

import com.paulpope.kpkaudiolibrary.data.model.books.Book;
import com.paulpope.kpkaudiolibrary.data.model.books.Textbook;
import com.paulpope.kpkaudiolibrary.data.model.books.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BookLibrary implements Serializable {

    private final static String BOOKS_FOLDER = "textbooks";
    private final static String WORKBOOKS_FOLDER = "workbooks";
    private final ArrayList<Book> books = new ArrayList<>();

    public BookLibrary(Context context) {

        try {
            loadBooksFromFile(context);
        } catch (IOException | ClassNotFoundException e) {
            try {
                updateFiles(context);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Collection<Book> getBooks() {
        return books;
    }

    private void updateFiles(Context context) throws IOException {
        for (var childFolderName : Objects.requireNonNull(context.getAssets().list(BOOKS_FOLDER))) {
            books.add(new Textbook(context, childFolderName, BOOKS_FOLDER + '/' + childFolderName));
        }

        for (var childFolderName : Objects.requireNonNull(context.getAssets().list(WORKBOOKS_FOLDER))) {
            books.add(new Workbook(context, childFolderName, WORKBOOKS_FOLDER + '/' + childFolderName));
        }

        saveBooksToFile(context);
    }

    private void saveBooksToFile(Context context) {
        try (FileOutputStream fos = context.openFileOutput("books.dat", Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(books);
        } catch (IOException e) {
            Log.e("BookLibrary", "Error saving books to file", e);
        }
    }

    private void loadBooksFromFile(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput("books.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        books.clear();
        books.addAll((ArrayList<Book>) ois.readObject());
    }
}
