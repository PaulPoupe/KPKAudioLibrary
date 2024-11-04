package com.example.kpkaudiolibrary.data.repository;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

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
