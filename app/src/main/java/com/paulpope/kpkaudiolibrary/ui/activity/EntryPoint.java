package com.paulpope.kpkaudiolibrary.ui.activity;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.ktx.Firebase;
import com.paulpope.kpkaudiolibrary.data.model.assetRepository.NameExtractor;
import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;
import com.paulpope.kpkaudiolibrary.data.repository.FirebaseController;

import java.util.ArrayList;

public class EntryPoint extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        NameExtractor.initialize(this);
        FirebaseApp.initializeApp(this);
        FirebaseController firebaseController = new FirebaseController();
        ArrayList<BookRef> r = (ArrayList<BookRef>) firebaseController.getAllFireBaseBooks();
    }
}
