package com.paulpope.kpkaudiolibrary.data.repository;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;
import com.paulpope.kpkaudiolibrary.data.model.books.BookTypes;

import java.util.ArrayList;
import java.util.Collection;

public class FirebaseController {
    public Collection<BookRef> getAllFireBaseBooks() {
        ArrayList<BookRef> booksRefs = new ArrayList<>();

        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        StorageReference textbooksFolderRef = rootRef.child("Textbook/");
        StorageReference workbookFoldrRef = rootRef.child("Workbook/");

        textbooksFolderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference textbookRef : listResult.getPrefixes()) {
                        Log.d("Firebase", "Textbook name: " + textbookRef.getName());
                        booksRefs.add(new BookRef(textbookRef.getName(), BookTypes.Textbook));
                    }
                }).addOnFailureListener(e -> {
                    Log.e("Firebase", "Ошибка: " + e.getMessage(), e);
                });;
        workbookFoldrRef.listAll().
                addOnSuccessListener(listResult -> {
                    for(StorageReference workbookRef : listResult.getPrefixes()){
                    Log.d("Firebase", "Workbook name: " + workbookRef.getName());
                    booksRefs.add(new BookRef(workbookRef.getName(), BookTypes.Workbook));
                    }
                }).addOnFailureListener(e -> {
                    Log.e("Firebase", "Ошибка: " + e.getMessage(), e);
                });
        return booksRefs;
    }
}
