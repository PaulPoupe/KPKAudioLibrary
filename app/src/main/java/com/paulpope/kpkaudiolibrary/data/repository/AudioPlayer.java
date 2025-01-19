package com.paulpope.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.paulpope.kpkaudiolibrary.data.model.exercises.Part;

import java.io.IOException;

public class AudioPlayer {
    private static final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Context context;
    private final AudioManager audioManager;
    private  final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    private static Animations animations;
    private static boolean isAudioLoaded;

    public AudioPlayer(Context context, Animations animations) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        AudioPlayer.animations = animations;
        audioFocusChangeListener = focusChange -> {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    animations.startAnimation();
                }
            }
        };
        updateAnimations();
    }

    private void updateAnimations(){
        if (isAudioLoaded){
            animations.progressBarLok(false);
        }
        if (mediaPlayer.isPlaying()) {
            animations.pauseAnimation();
        } else if (!mediaPlayer.isPlaying() && isAudioLoaded) {
            animations.startAnimation();
        }
    }

    public void play(Part part) throws Exception {
        if (requestAudioFocus()) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            isAudioLoaded = false;

            AssetFileDescriptor audioAfd = getAssetFileDescriptor(part.getAudioFilePath());

            mediaPlayer.setDataSource(audioAfd.getFileDescriptor(), audioAfd.getStartOffset(), audioAfd.getLength());
            audioAfd.close();

            mediaPlayer.prepare();
            mediaPlayer.start();

            animations.pauseAnimation();
            isAudioLoaded = true;
            startProgressBarAnimation();

            mediaPlayer.setOnCompletionListener(mp -> {
                mp.reset();
                isAudioLoaded = false;
                abandonAudioFocus();
                animations.startAnimation();
                animations.progressBarLok(true);
            });
        }
    }

    private void startProgressBarAnimation() {
        animations.progressBarLok(false);

        new Thread(() -> {
            while (isAudioLoaded) {
                float progress = ((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 10000;
                animations.progressBarAnimation(progress);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            animations.progressBarAnimation(0);
        }).start();
    }


    public void pause(){
        mediaPlayer.pause();
        animations.startAnimation();
    }

    public void continuePlaying(){
        mediaPlayer.start();
        animations.pauseAnimation();
    }

    public void forward(){
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
    }

    public void replay(){
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
    }

    public void seekTo(int requiredValue){
        mediaPlayer.seekTo(mediaPlayer.getDuration() * requiredValue / 10000);
    }

    public boolean isPlaying (){
        return mediaPlayer.isPlaying();
    }

    public boolean isAudioLoaded() {
        return isAudioLoaded;
    }

    private boolean requestAudioFocus() {
        int result = audioManager.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
        );

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private void abandonAudioFocus() {
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    private AssetFileDescriptor getAssetFileDescriptor(String path) throws IOException {
        return context.getAssets().openFd(path);
    }

    public interface Animations {
        void startAnimation();
        void pauseAnimation();
        void progressBarAnimation(float currentPosition);
        void progressBarLok(boolean isLocked);
    }
}