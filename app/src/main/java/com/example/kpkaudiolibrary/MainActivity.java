package com.example.kpkaudiolibrary;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private LinearLayout bookCard1;
    private LinearLayout bookCard2;
    private LinearLayout workbook1;
    private LinearLayout workbook2;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookCard1 = findViewById(R.id.layoutBook1);
        bookCard2 = findViewById(R.id.layoutBook2);
        workbook1 = findViewById(R.id.layoutWorkBook1);
        workbook2 = findViewById(R.id.layoutWorkBook2);
    }
}