package com.paulpope.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.util.Log;

import com.paulpope.kpkaudiolibrary.data.model.books.Book;
import com.paulpope.kpkaudiolibrary.data.model.books.Textbook;
import com.paulpope.kpkaudiolibrary.data.model.books.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookLibrary implements Serializable {
    private static final String TAG = "BookLibrary";

    public static final String TEXTBOOKS_FOLDER = "textbooks";
    public static final String WORKBOOKS_FOLDER = "workbooks";
    private static final String DATA_FILE_NAME = "books.dat";

    private final ArrayList<Book> books = new ArrayList<>();

    public BookLibrary(Context context) {
        createRequiredDirectories(context);

        try {
            loadBooksFromFile(context);
        } catch (IOException | ClassNotFoundException e) {
            Log.w(TAG, "Failed to load books from file, updating files", e);
            try {
                updateFiles(context);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to update files", ex);
            }
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    private void createRequiredDirectories(Context context) {
        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            Log.e(TAG, "External storage not available");
            return;
        }

        File textbooksDir = new File(baseDir, TEXTBOOKS_FOLDER);
        File workbooksDir = new File(baseDir, WORKBOOKS_FOLDER);

        if (!textbooksDir.exists() && textbooksDir.mkdirs()) {
            Log.i(TAG, "Created directory: " + textbooksDir.getAbsolutePath());
        } else if (!textbooksDir.exists()) {
            Log.e(TAG, "Failed to create directory: " + textbooksDir.getAbsolutePath());
        }

        if (!workbooksDir.exists() && workbooksDir.mkdirs()) {
            Log.i(TAG, "Created directory: " + workbooksDir.getAbsolutePath());
        } else if (!workbooksDir.exists()) {
            Log.e(TAG, "Failed to create directory: " + workbooksDir.getAbsolutePath());
        }
    }

    public void updateFiles(Context context) throws IOException {
        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            throw new IOException("External storage not available");
        }

        File textbooksDir = new File(baseDir, TEXTBOOKS_FOLDER);
        File workbooksDir = new File(baseDir, WORKBOOKS_FOLDER);

        for (File child : Objects.requireNonNull(textbooksDir.listFiles())) {
            if (child.isDirectory()) {
                books.add(new Textbook(context, child.getName(), child));
            }
        }

        for (File child : Objects.requireNonNull(workbooksDir.listFiles())) {
            if (child.isDirectory()) {
                books.add(new Workbook(context, child.getName(), child));
            }
        }

        saveBooksToFile(context);
    }

    private void saveBooksToFile(Context context) {
        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            Log.e(TAG, "External storage not available");
            return;
        }

        File dataFile = new File(baseDir, DATA_FILE_NAME);

        try (FileOutputStream fos = new FileOutputStream(dataFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(books);
            Log.i(TAG, "Books saved successfully to " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error saving books to file", e);
        }
    }
    private void loadBooksFromFile(Context context) throws IOException, ClassNotFoundException {
        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            throw new IOException("External storage not available");
        }

        File dataFile = new File(baseDir, DATA_FILE_NAME);

        if (!dataFile.exists()) {
            throw new IOException("Data file not found: " + dataFile.getAbsolutePath());
        }

        try (FileInputStream fis = new FileInputStream(dataFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            books.clear();
            books.addAll((ArrayList<Book>) ois.readObject());
            Log.i(TAG, "Books loaded successfully from " + dataFile.getAbsolutePath());
        }
    }

    public void deleteBooksDataFile(Context context) {
        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            Log.e(TAG, "External storage not available");
            return;
        }

        File dataFile = new File(baseDir, DATA_FILE_NAME);

        if (dataFile.exists()) {
            if (dataFile.delete()) {
                Log.i(TAG, "Deleted books.dat successfully");
            } else {
                Log.e(TAG, "Failed to delete books.dat");
            }
        } else {
            Log.i(TAG, "books.dat does not exist");
        }
    }
}