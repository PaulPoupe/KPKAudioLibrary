package com.example.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.example.kpkaudiolibrary.data.model.Part;

import java.io.IOException;

public class AudioPlayer {
    private static final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Context context;
    private final AudioManager audioManager;
    private  final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    private static Animations animations;

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
        if (mediaPlayer.isPlaying()) {
            animations.pauseAnimation();
        } else {
            animations.startAnimation();
        }
    }

    public void play(Part part) throws Exception {
        if (requestAudioFocus()) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();

            AssetFileDescriptor audioAfd = getAssetFileDescriptor(part.getAudioFilePath());

            mediaPlayer.setDataSource(audioAfd.getFileDescriptor(), audioAfd.getStartOffset(), audioAfd.getLength());
            audioAfd.close();

            mediaPlayer.prepare();
            mediaPlayer.start();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (mediaPlayer.isPlaying()) {
                        animations.progressBarAnimation(((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100);
                    }
                }
            }).start();
            
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.reset();
                abandonAudioFocus();
                animations.startAnimation();
                animations.progressBarAnimation(0);
            });

        }
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
        mediaPlayer.seekTo(mediaPlayer.getDuration() * requiredValue / 100);
    }

    public boolean isPlaying (){
        return mediaPlayer.isPlaying();
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
    }
}