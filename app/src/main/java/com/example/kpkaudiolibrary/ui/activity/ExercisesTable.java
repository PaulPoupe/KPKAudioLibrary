package com.example.kpkaudiolibrary.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.kpkaudiolibrary.data.repository.AudioPlayer;

import java.util.Objects;

public class ExercisesTable extends AppCompatActivity {
    private Lesson lesson;
    private TextView lessonName;
    private LinearLayout exercisesList;
    AudioPlayer audioPlayer;


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
        audioPlayer = new AudioPlayer(this);

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

        for (Exercise exercise : lesson) {
            View exerciseView = inflater.inflate(R.layout.exercise_item, exercisesList, false);

            TextView exerciseNumber = exerciseView.findViewById(R.id.exercise_number);
            LinearLayout exercisePartsRoot = exerciseView.findViewById(R.id.part_buttons_root);

            exerciseNumber.setText(String.valueOf(exercise.getExerciseNumber()));
            createExercisePartsPanels(exercise, exercisePartsRoot);
            exercisesList.addView(exerciseView);
        }
    }

    private void createExercisePartsPanels(Exercise exercise, ViewGroup partsRoot){
        LayoutInflater inflater = LayoutInflater.from(this);

        for (var part : exercise){
            View partView = inflater.inflate(R.layout.exercise_part_button, partsRoot, false);

            TextView partName = partView.findViewById(R.id.exercise_part_button);
            //partName.setText(part.get())

            partView.setOnClickListener(v -> {
                try {
                    audioPlayer.play(part);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            partsRoot.addView(partView);
        }
    }
}