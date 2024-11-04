package com.example.kpkaudiolibrary.data.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.example.kpkaudiolibrary.data.model.BookLibrary;

import java.io.IOException;

public class AudioPlayer {
    MediaPlayer mediaPlayer = new MediaPlayer();
    public void play(AssetFileDescriptor audioAfd) throws Exception {
        mediaPlayer.setDataSource(audioAfd.getFileDescriptor(), audioAfd.getStartOffset(), audioAfd.getLength());
        audioAfd.close();

        mediaPlayer.prepare();
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
        });
    }
}
