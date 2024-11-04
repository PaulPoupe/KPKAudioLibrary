package com.example.kpkaudiolibrary.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kpkaudiolibrary.R;
import com.example.kpkaudiolibrary.data.model.BookLibrary;
import com.example.kpkaudiolibrary.data.model.Lesson;
import com.example.kpkaudiolibrary.data.repository.AudioPlayer;

import java.io.IOException;

public class contentsTable extends AppCompatActivity {
    private AudioPlayer audioPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contents_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout lessonsTable = findViewById(R.id.lessonList);
        LayoutInflater inflater = LayoutInflater.from(this);
        BookLibrary bookLibrary = null;
        try {
           bookLibrary = new BookLibrary(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (Lesson lesson : bookLibrary.getBook1()) {
            View lessonView = inflater.inflate(R.layout.lesson_item, lessonsTable, false);

            TextView lessonNumber = lessonView.findViewById(R.id.lesson_number);
            TextView lessonName = lessonView.findViewById(R.id.lesson_name);

            lessonNumber.setText(String.valueOf( lesson.getLessonNumber()));
            lessonName.setText("Lesson " + (lesson.getLessonNumber()));

            lessonsTable.addView(lessonView);
        }
    }
}