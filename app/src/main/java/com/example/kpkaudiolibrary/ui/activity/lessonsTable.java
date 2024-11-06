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
import com.example.kpkaudiolibrary.data.model.Book;
import com.example.kpkaudiolibrary.data.model.BookLibrary;
import com.example.kpkaudiolibrary.data.model.Lesson;

public class lessonsTable extends AppCompatActivity {
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

        Bundle arguments = getIntent().getExtras();
        LayoutInflater inflater = LayoutInflater.from(this);

        LinearLayout lessonsTable = findViewById(R.id.lessonList);
        TextView bookName = findViewById(R.id.book_name);
        TextView bookDescription = findViewById(R.id.book_description);
        BookLibrary bookLibrary = new BookLibrary(this);

        Book book = bookLibrary.getBook(arguments.containsKey("bookNumber") ? arguments.getInt("bookNumber") : arguments.getInt("workbookNumber"));

        initializeHeaderOfActivity(bookName, arguments, bookDescription);
        createLessonPanels(book, inflater, lessonsTable);
    }

    private void initializeHeaderOfActivity(TextView bookName, Bundle arguments, TextView bookDescription) {
        bookName.setText(arguments.getString("bookName"));
        bookDescription.setText(arguments.getString("bookDescription"));
    }

    private void createLessonPanels(Book book, LayoutInflater inflater, LinearLayout lessonsTable) {
        for (Lesson lesson : book) {
            View lessonView = inflater.inflate(R.layout.lesson_item, lessonsTable, false);

            TextView lessonNumber = lessonView.findViewById(R.id.lesson_number);
            TextView lessonName = lessonView.findViewById(R.id.lesson_name);

            lessonNumber.setText(String.valueOf(lesson.getLessonNumber()));
            lessonName.setText("Lesson " + (lesson.getLessonNumber()));

            lessonsTable.addView(lessonView);
        }
    }
}