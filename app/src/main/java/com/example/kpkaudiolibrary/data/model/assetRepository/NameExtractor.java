package com.example.kpkaudiolibrary.data.model.assetRepository;

import android.content.Context;
import android.content.res.AssetManager;

import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class NameExtractor {
    static private Context context;

    public static String extractName(Context context, String assetFilePath) throws Exception {
        // Копируем файл из assets во временную директорию
        NameExtractor.context = context;
        File tempFile = copyAssetToTempFile(context, assetFilePath);

        // Используем mp3agic для чтения метаданных
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

    public static String findFileNameByPart(String partialName, String path) throws Exception {
        // Получаем список всех файлов в assets
        AssetManager assetManager = context.getAssets();
        String[] assetFiles = assetManager.list(path);  // список всех файлов в assets

        // Проходим по каждому файлу
        for (String assetFilePath : assetFiles) {
            // Копируем файл из assets во временную директорию
            File tempFile = copyAssetToTempFile(context, path + assetFilePath);

            // Используем mp3agic для чтения метаданных
            Mp3File mp3file = new Mp3File(tempFile.getAbsolutePath());

            String fullTitle = null;

            // Получаем название из тегов ID3v2 или ID3v1
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                fullTitle = id3v2Tag.getTitle();
            } else if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                fullTitle = id3v1Tag.getTitle();
            }

            // Если название файла содержит подстроку (partialName), возвращаем имя файла
            if (fullTitle != null && fullTitle.contains(partialName)) {
                return assetFilePath;  // Возвращаем имя файла из assets
            }
        }

        throw new Exception("Файл с таким названием не найден.");
    }

    public static String[] updateExerciseNumbers(String[] exercises) {
        // Итерация по массиву упражнений, начиная с 1-го элемента
        for (int i = 1; i < exercises.length; i++) {
            String current = exercises[i];
            String previous = exercises[i - 1];

            // Получаем первое число из текущего и предыдущего упражнения
            String currentNumber = getNumber(current);
            String previousNumber = getNumber(previous);

            // Если текущее число на 1 больше предыдущего, меняем его на предыдущий номер
            if (currentNumber != null && previousNumber != null && Integer.parseInt(currentNumber) == Integer.parseInt(previousNumber) + 1) {
                // Заменяем номер на номер предыдущего
                exercises[i] = current.replaceFirst("^\\d{3}", previousNumber);
            }
        }
        return exercises;
    }

    // Метод для извлечения числа из строки
    private static String getNumber(String str) {
        String[] parts = str.split(" ");
        return parts[0].matches("\\d{3}") ? parts[0] : null;
    }
}
