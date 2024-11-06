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

        Intent intent = new Intent(this, lessonsTable.class);
        LinearLayout bookCard1 = findViewById(R.id.layoutBook1);
        LinearLayout bookCard2 = findViewById(R.id.layoutBook2);
        LinearLayout workbook1 = findViewById(R.id.layoutWorkBook1);
        LinearLayout workbook2 = findViewById(R.id.layoutWorkBook2);

        bookCard1.setOnClickListener(v -> {
            intent.putExtra("bookName","Krok po kroku 1" );
            intent.putExtra("bookDescription", "Podręcznik");
            intent.putExtra("bookNumber",0 );
            startActivity(intent);
        });

        bookCard2.setOnClickListener(v -> {
            intent.putExtra("bookName","Krok po kroku 2" );
            intent.putExtra("bookDescription", "Podręcznik");
            intent.putExtra("bookNumber",1 );
            startActivity(intent);
        });

        workbook1.setOnClickListener(v -> {
            intent.putExtra("bookName","Krok po kroku 1" );
            intent.putExtra("bookDescription", "Zeszyt cwiczen");
            intent.putExtra("workbookNumber",0 );
            startActivity(intent);
        });

        workbook2.setOnClickListener(v -> {
            intent.putExtra("bookName","Krok po kroku 2" );
            intent.putExtra("bookDescription", "Zeszyt cwiczen");
            intent.putExtra("workbookNumber",1);
            startActivity(intent);
        });
    }
}