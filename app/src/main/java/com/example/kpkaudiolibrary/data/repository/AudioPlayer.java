package com.example.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.example.kpkaudiolibrary.data.model.Part;

import java.io.IOException;

public class AudioPlayer {
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Context context;


    public AudioPlayer(Context context) {
        this.context = context;
    }

    public void play(Part part) throws Exception {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        AssetFileDescriptor audioAfd = getAssetFileDescriptor(part.getAudioFilePath());

        mediaPlayer.setDataSource(audioAfd.getFileDescriptor(), audioAfd.getStartOffset(), audioAfd.getLength());
        audioAfd.close();

        mediaPlayer.prepare();
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
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

    private AssetFileDescriptor getAssetFileDescriptor(String path) throws IOException {
        return context.getAssets().openFd(path);
    }
}
