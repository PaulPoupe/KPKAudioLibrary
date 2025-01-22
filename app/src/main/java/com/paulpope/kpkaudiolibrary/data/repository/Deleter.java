package com.paulpope.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class Deleter {
    private static final String TAG = "Deleter";
    public void deleteFilesInDirectory(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                   if (file.delete()){
                       Log.d(TAG,"File deleted successfully");
                   }else{
                       Log.w(TAG,"Failed to delete file");
                   }
                }
            }
        }
    }

    public void deleteDirectory(File folder) {
        deleteFilesInDirectory(folder);

        if (folder.exists()) {
            if(folder.delete()){
                Log.d(TAG,"Directory deleted successfully");
            }else{
                Log.w(TAG,"Failed to delete directory");
            }
        }
    }
}
