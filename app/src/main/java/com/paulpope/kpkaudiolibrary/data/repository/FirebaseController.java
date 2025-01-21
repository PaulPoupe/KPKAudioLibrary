package com.paulpope.kpkaudiolibrary.data.repository;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;
import com.paulpope.kpkaudiolibrary.data.model.books.BookTypes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FirebaseController {
    private static final String TAG = "FirebaseController";
    public void getAllFireBaseBooks(FirebaseBooksCallback callback) {
        List<BookRef> booksRefs = new CopyOnWriteArrayList<>();
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        StorageReference textbooksFolderRef = rootRef.child("Textbook/");
        StorageReference workbookFoldrRef = rootRef.child("Workbook/");


        final int[] tasksCompleted = {0};


        Runnable onComplete = () -> {
            if (tasksCompleted[0] == 2) {
                callback.onBooksLoaded(booksRefs);
                Log.d(TAG, "Firebase gave books refs");
            }
        };

        textbooksFolderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference textbookRef : listResult.getPrefixes()) {
                        Log.d(TAG, "Textbook name: " + textbookRef.getName());
                        booksRefs.add(new BookRef(textbookRef.getName(), BookTypes.Textbook, textbookRef));
                    }
                    synchronized (tasksCompleted) {
                        tasksCompleted[0]++;
                    }
                    onComplete.run();
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Error: " + e.getMessage(), e);
                    synchronized (tasksCompleted) {
                        tasksCompleted[0]++;
                    }
                    onComplete.run();
                });

        workbookFoldrRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference workbookRef : listResult.getPrefixes()) {
                        Log.d(TAG, "Workbook name: " + workbookRef.getName());
                        booksRefs.add(new BookRef(workbookRef.getName(), BookTypes.Workbook, workbookRef));
                    }
                    synchronized (tasksCompleted) {
                        tasksCompleted[0]++;
                    }
                    onComplete.run();
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Error: " + e.getMessage(), e);
                    synchronized (tasksCompleted) {
                        tasksCompleted[0]++;
                    }
                    onComplete.run();
                });
    }
}
