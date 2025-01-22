package com.paulpope.kpkaudiolibrary.data.repository;

import static com.paulpope.kpkaudiolibrary.data.repository.BookLibrary.TEXTBOOKS_FOLDER;
import static com.paulpope.kpkaudiolibrary.data.repository.BookLibrary.WORKBOOKS_FOLDER;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.storage.StorageReference;
import com.paulpope.kpkaudiolibrary.R;
import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;
import com.paulpope.kpkaudiolibrary.data.model.books.BookTypes;

import java.io.File;

public class BookDownloader {
    private static final String TAG = "FirebaseBookDownloader";
    private ProgressDialog progressDialog;

    public void downloadBooks(Context context, BookRef bookRef, DownloadUiUpdateCallback uiUpdatedCallback, DownloadCompletedCallback callback) {
        // Показываем ProgressDialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false); // Запрещаем закрытие
        progressDialog.setMessage(context.getString(R.string.downloading_book));
        progressDialog.show();

        String baseFolder = bookRef.getBookType() == BookTypes.Textbook ? TEXTBOOKS_FOLDER : WORKBOOKS_FOLDER;

        int[] tasksCompleted = {0};

        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            Log.e(TAG, "External storage not available");
            return;
        }

        File targetFolder = new File(new File(baseDir, baseFolder), bookRef.getLanguageLevel().name());

        if (!targetFolder.exists() && !targetFolder.mkdirs()) {
            Log.e(TAG, "Failed to create target folder: " + targetFolder.getAbsolutePath());
            return;
        }

        StorageReference folderRef = bookRef.getRef();
        folderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference fileRef : listResult.getItems()) {
                        downloadFile(fileRef, targetFolder, tasksCompleted, listResult.getItems().size(), uiUpdatedCallback, callback);
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Failed to list files in folder: " + folderRef.getPath(), e));
    }

    private void downloadFile(StorageReference fileRef, File targetFolder, int[] tasksCompleted, int tasksCount, DownloadUiUpdateCallback uiUpdatedCallback, DownloadCompletedCallback callback) {
        Runnable onComplete = () -> {
            uiUpdatedCallback.onUpdate((int) ((tasksCompleted[0] * 100.0) / tasksCount));
            if (tasksCompleted[0] == tasksCount) {
                callback.onBookLoaded();
                progressDialog.dismiss(); // Закрываем диалог по завершению скачивания
                Log.d(TAG, "Firebase downloaded book successfully");
            }
        };

        try {
            File targetFile = new File(targetFolder, fileRef.getName());
            fileRef.getFile(targetFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Log.i(TAG, "File downloaded: " + targetFile.getAbsolutePath());
                        synchronized (tasksCompleted) {
                            tasksCompleted[0]++;
                        }
                        onComplete.run();
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Failed to download file: " + fileRef.getPath(), e));
        } catch (Exception e) {
            Log.e(TAG, "Error initializing file download for: " + fileRef.getPath(), e);
        }
    }
}
