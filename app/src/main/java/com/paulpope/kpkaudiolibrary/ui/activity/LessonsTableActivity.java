package com.paulpope.kpkaudiolibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.paulpope.kpkaudiolibrary.R;
import com.paulpope.kpkaudiolibrary.data.model.books.Book;
import com.paulpope.kpkaudiolibrary.data.model.lessons.Lesson;

public class LessonsTableActivity extends BaseActivity {
    public static final String LESSON_KEY = "lesson";
    protected Book book;
    private TextView bookName;
    private TextView bookDescription;
    LinearLayout lessonsTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workbook_lessons_table);
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
        bookName.setText(getString(R.string.book_name, book.getLanguageLevel().name()));
        switch (book.getBookType()) {
            case Textbook:
                bookDescription.setText(R.string.book_type);
                break;

            case Workbook:
                bookDescription.setText(R.string.workbook_type);
                break;
        }
    }

    private Book takeBook() {
        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        book = (Book) arguments.getSerializable(BookTableActivity.BOOK_KEY);
        return book;
    }

    protected void createLessonPanels() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Lesson lesson : book.getLessons()) {
            View lessonView = inflater.inflate(R.layout.lesson_item, lessonsTable, false);

            TextView lessonNumber = lessonView.findViewById(R.id.lesson_number);
            TextView lessonName = lessonView.findViewById(R.id.lesson_name);

            lessonNumber.setText(String.valueOf(lesson.getNumber()));
            lessonName.setText(lesson.getName());

            lessonView.setOnClickListener(v -> {
                Intent intent = new Intent(this, ExercisesTableActivity.class);
                intent.putExtra(LESSON_KEY, lesson);
                startActivity(intent);
            });

            lessonsTable.addView(lessonView);
        }
    }
}