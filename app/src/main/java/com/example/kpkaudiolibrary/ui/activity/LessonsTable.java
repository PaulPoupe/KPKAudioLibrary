package com.example.kpkaudiolibrary.ui.activity;

import android.content.Intent;
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
import com.example.kpkaudiolibrary.data.model.Book;
import com.example.kpkaudiolibrary.data.model.Lesson;

public class LessonsTable extends AppCompatActivity {
    public static final String LESSON_KEY = "lesson";
    private Book book;
    private TextView bookName;
    private TextView bookDescription;
    LinearLayout lessonsTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lessons_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lessonsTable = findViewById(R.id.lessonList);
        bookName = findViewById(R.id.book_name);
        bookDescription = findViewById(R.id.book_description);
        book = takeBook();

        initializeHeaderOfActivity();
        createLessonPanels();
    }

    private void initializeHeaderOfActivity() {
        bookName.setText("Krok po kroku " + book.getLanguageLevel().name());
        bookDescription.setText(book.getBookType().name());
    }

    private Book takeBook() {
        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        book = (Book) arguments.getSerializable(MainActivity.BOOK_KEY);
        return book;
    }

    private void createLessonPanels() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Lesson lesson : book) {
            View lessonView = inflater.inflate(R.layout.lesson_item, lessonsTable, false);

            TextView lessonNumber = lessonView.findViewById(R.id.lesson_number);
            TextView lessonName = lessonView.findViewById(R.id.lesson_name);

            lessonNumber.setText(String.valueOf(lesson.getLessonNumber()));
            lessonName.setText(lesson.getLessonName());

            lessonView.setOnClickListener(v -> {
                Intent intent = new Intent(this, ExercisesTable.class);
                intent.putExtra(LESSON_KEY, lesson);
                startActivity(intent);
            });

            lessonsTable.addView(lessonView);
        }
    }
}