package com.example.kpkaudiolibrary.ui.activity;

import android.app.Application;

import com.example.kpkaudiolibrary.data.model.assetRepository.NameExtractor;

public class EntryPoint extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        NameExtractor.initialize(this);
    }
}
