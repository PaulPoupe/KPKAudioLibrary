package com.paulpope.kpkaudiolibrary.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.paulpope.kpkaudiolibrary.R;
import com.paulpope.kpkaudiolibrary.data.model.exercises.Exercise;
import com.paulpope.kpkaudiolibrary.data.model.exercises.TextbookExercise;
import com.paulpope.kpkaudiolibrary.data.model.exercises.WorkbookExercise;
import com.paulpope.kpkaudiolibrary.data.model.lessons.Lesson;
import com.paulpope.kpkaudiolibrary.data.repository.AudioPlayer;

import java.util.Objects;

public class ExercisesTableActivity extends BaseActivity {
    private Lesson lesson;
    private TextView lessonName;
    private LinearLayout exercisesList;
    private LinearLayout bottomPanel;
    private ImageButton playButton;
    private ImageButton replayButton;
    private ImageButton forwardButton;
    private SeekBar progressBar;
    private AudioPlayer audioPlayer;


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
        bottomPanel = findViewById(R.id.bottom_panel);
        playButton = findViewById(R.id.play_button);
        forwardButton = findViewById(R.id.forward_button);
        replayButton = findViewById(R.id.replay_button);
        progressBar = findViewById(R.id.progress_bar);
        audioPlayer = new AudioPlayer(this, new AudioPlayer.Animations() {
            @Override
            public void startAnimation() {
                playButton.setImageResource(R.drawable.ic_play);
            }

            @Override
            public void pauseAnimation() {
                playButton.setImageResource(R.drawable.ic_pause);
            }

            @Override
            public void progressBarAnimation(float currentPosition) {
                progressBar.setProgress((int) currentPosition);
            }

            @Override
            public void progressBarLok(boolean isLocked) {
                progressBar.setEnabled(!isLocked);
                progressBar.setVisibility(isLocked ? View.GONE : View.VISIBLE);
                bottomPanel.setVisibility(isLocked ? View.GONE : View.VISIBLE);
            }
        });

        initializeHeaderOfActivity();
        createExercisePanels();
        initializeBottomPanelOfActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioPlayer.pause();
    }

    private void initializeHeaderOfActivity() {
        lessonName.setText(lesson.getName());
    }

    private void initializeBottomPanelOfActivity() {
        playButton.setOnClickListener(v -> {
            if (audioPlayer.isPlaying()) {
                audioPlayer.pause();
            } else if (!audioPlayer.isPlaying() && audioPlayer.isAudioLoaded()) {
                audioPlayer.continuePlaying();
            }
        });
        progressBar.setEnabled(audioPlayer.isAudioLoaded());
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audioPlayer.seekTo(seekBar.getProgress());
            }
        });
        forwardButton.setOnClickListener(v -> audioPlayer.forward());
        replayButton.setOnClickListener(v -> audioPlayer.replay());
        bottomPanel.setVisibility(audioPlayer.isAudioLoaded() ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(audioPlayer.isAudioLoaded() ? View.VISIBLE : View.GONE);
    }

    private Lesson takeLesson() {
        Bundle params = getIntent().getExtras();
        return (Lesson) Objects.requireNonNull(params).getSerializable(LessonsTableActivity.LESSON_KEY);
    }

    private void createExercisePanels() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Exercise exercise : lesson) {
            View exerciseView;


            if (exercise instanceof WorkbookExercise) {
                exerciseView = inflater.inflate(R.layout.exercise_item, exercisesList, false);
                createExercisePartsPanels(exercise, exerciseView.findViewById(R.id.part_buttons_root));
            } else {
                TextbookExercise textbookExercise = (TextbookExercise) exercise;

                exerciseView = inflater.inflate(R.layout.exercise_textbook_item, exercisesList, false);

                TextView exerciseName = exerciseView.findViewById(R.id.exercise_name);
                exerciseName.setText(textbookExercise.getName());

                TextView exerciseStartButton = exerciseView.findViewById(R.id.exercise_start_button);
                exerciseStartButton.setOnClickListener(v -> {
                    try {
                        audioPlayer.play(textbookExercise.getPart());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            TextView exerciseNumber = exerciseView.findViewById(R.id.exercise_number);
            exerciseNumber.setText(String.valueOf(exercise.getNumber()));

            exercisesList.addView(exerciseView);
        }
    }

    private void createExercisePartsPanels(Exercise exercise, ViewGroup partsRoot) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (var part : exercise) {
            View partView;

            if (!(exercise.getPartsCount() == 1)) {
                partView = inflater.inflate(R.layout.exercise_part_button, partsRoot, false);
                TextView partName = partView.findViewById(R.id.exercise_part_button);
                partName.setText(part.getName());
            } else {
                partView = inflater.inflate(R.layout.exercise_start_button, partsRoot, false);
            }

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