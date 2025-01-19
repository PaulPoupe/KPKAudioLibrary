package com.paulpope.kpkaudiolibrary.ui.activity;

import android.app.Application;

import com.paulpope.kpkaudiolibrary.data.model.assetRepository.NameExtractor;

public class EntryPoint extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        NameExtractor.initialize(this);
    }
}
