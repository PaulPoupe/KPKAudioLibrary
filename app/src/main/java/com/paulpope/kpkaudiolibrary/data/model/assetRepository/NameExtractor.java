package com.paulpope.kpkaudiolibrary.data.model.assetRepository;

import android.content.Context;
import android.content.res.AssetManager;

import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class NameExtractor {
    private static Context context;

    public static void initialize(Context context) {
        NameExtractor.context = context;
    }
    public static String extractName(String assetFilePath) throws Exception {

        File tempFile = copyAssetToTempFile(context, assetFilePath);

        Mp3File mp3file = new Mp3File(tempFile.getAbsolutePath());

        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            return id3v2Tag.getTitle();
        } else if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
            return id3v1Tag.getTitle();
        } else {
            throw new Exception("Теги не найдены.");
        }
    }

    private static File copyAssetToTempFile(Context context, String assetFilePath) throws Exception {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(assetFilePath);
        File tempFile = File.createTempFile("temp_audio", ".mp3", context.getCacheDir());
        tempFile.deleteOnExit();

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            inputStream.close();
        }

        return tempFile;
    }
}
