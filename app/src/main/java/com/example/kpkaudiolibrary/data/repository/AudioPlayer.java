package com.example.kpkaudiolibrary.data.repository;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.example.kpkaudiolibrary.data.model.BookLibrary;

import java.io.IOException;

public class AudioPlayer {
    MediaPlayer mediaPlayer = new MediaPlayer();
    private final BookLibrary bookLibrary;

    public AudioPlayer(Context context) throws IOException {
        bookLibrary = new BookLibrary(context);
    }

    public void play(int lessonNumber, int exerciseNumber, String partName) throws Exception {
        AssetFileDescriptor afd = bookLibrary.getAudioFileAfd(lessonNumber, exerciseNumber, partName);
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        afd.close();

        mediaPlayer.prepare();
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
        });
    }
}
