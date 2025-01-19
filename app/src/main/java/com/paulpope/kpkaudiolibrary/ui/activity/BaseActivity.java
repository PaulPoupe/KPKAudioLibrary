package com.paulpope.kpkaudiolibrary.ui.activity;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void attachBaseContext(Context newBase) {
        String language = newBase.getSharedPreferences("app_preferences", MODE_PRIVATE).getString("language", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = new Configuration(newBase.getResources().getConfiguration());
        config.setLocale(locale);
        super.attachBaseContext(newBase.createConfigurationContext(config));
    }
}
