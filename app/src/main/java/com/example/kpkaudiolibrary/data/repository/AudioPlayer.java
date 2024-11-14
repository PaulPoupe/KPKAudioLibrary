package com.example.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.example.kpkaudiolibrary.data.model.Part;

import java.io.IOException;

public class AudioPlayer {
    private static final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Context context;
    private final AudioManager audioManager;
    private final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    public AudioPlayer(Context context) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        audioFocusChangeListener = focusChange -> {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        };
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

            mediaPlayer.setOnCompletionListener(mp -> {
                mp.reset();
                abandonAudioFocus();
            });
        }
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void continuePlaying(){
        mediaPlayer.start();
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
}