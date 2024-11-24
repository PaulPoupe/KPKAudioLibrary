package com.example.kpkaudiolibrary.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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

public class ExercisesTableActivity extends BaseActivity {
    private Lesson lesson;
    private TextView lessonName;
    private LinearLayout exercisesList;
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
            }
        });

        initializeHeaderOfActivity();
        createExercisePanels();
        initializeBottomPanelOfActivity();
    }

    private void initializeHeaderOfActivity() {
        lessonName.setText(lesson.getLessonName());
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
    }

    private Lesson takeLesson() {
        Bundle params = getIntent().getExtras();
        return (Lesson) Objects.requireNonNull(params).getSerializable(LessonsTableActivity.LESSON_KEY);
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