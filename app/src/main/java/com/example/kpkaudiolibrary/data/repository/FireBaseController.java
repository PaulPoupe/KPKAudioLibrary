package com.example.kpkaudiolibrary.data.repository;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

public class FireBaseController {
    public void getFireBaseUploadedBooks() {
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        StorageReference directoryRef = rootRef.child("textbooks/");

        directoryRef.listAll()
                .addOnSuccessListener(listResult -> {
                    // Обрабатываем подпапки (prefixes)
                    for (StorageReference prefix : listResult.getPrefixes()) {
                        // Здесь prefix представляет собой подпапку
                        System.out.println("Папка: " + prefix.getName());
                    }
                    // Обрабатываем файлы (items)
                    for (StorageReference item : listResult.getItems()) {
                        // Здесь item представляет собой файл
                        System.out.println("Файл: " + item.getName());
                    }
                })
                .addOnFailureListener(e -> {
                    // Обработка ошибок
                    System.err.println("Ошибка: " + e.getMessage());
                });

    }
}
