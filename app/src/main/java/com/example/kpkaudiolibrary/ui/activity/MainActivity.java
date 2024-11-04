package com.example.kpkaudiolibrary.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import com.example.kpkaudiolibrary.R;


public class MainActivity extends AppCompatActivity {

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

        LinearLayout bookCard1 = findViewById(R.id.layoutBook1);
        LinearLayout bookCard2 = findViewById(R.id.layoutBook2);
        LinearLayout workbook1 = findViewById(R.id.layoutWorkBook1);
        LinearLayout workbook2 = findViewById(R.id.layoutWorkBook2);

        bookCard1.setOnClickListener(v -> {
            Intent intent = new Intent(this, contentsTable.class);
            startActivity(intent);
        });

        bookCard2.setOnClickListener(v -> {
        });

        workbook1.setOnClickListener(v -> {

        });

        workbook2.setOnClickListener(v -> {

        });
    }
}