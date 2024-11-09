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
import com.example.kpkaudiolibrary.data.model.Exercise;
import com.example.kpkaudiolibrary.data.model.Lesson;

import java.util.Objects;

public class ExercisesTable extends AppCompatActivity {
    private Lesson lesson;
    private TextView lessonName;
    private LinearLayout exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercises_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lessonName = findViewById(R.id.lesson_name);
        lesson = takeLesson();
        exercisesList = findViewById(R.id.exerciseList);

        initializeHeaderOfActivity();
        createExercisePanels();
    }

    private void initializeHeaderOfActivity(){
        lessonName.setText(lesson.getLessonName());
    }

    private Lesson takeLesson() {
        LinearLayout exercisesList = findViewById(R.id.exerciseList);
        Bundle params  = getIntent().getExtras();
        Lesson lesson = (Lesson) Objects.requireNonNull(params).getSerializable(LessonsTable.LESSON_KEY);
        return lesson;
    }

    private void createExercisePanels() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Exercise exercises : lesson) {
            View exerciseView = inflater.inflate(R.layout.exercise_item, exercisesList, false);

            TextView exerciseNumber = exerciseView.findViewById(R.id.exercise_number);

            exerciseNumber.setText(String.valueOf(exercises.getExerciseNumber()));

            exercisesList.addView(exerciseView);
        }
    }
}