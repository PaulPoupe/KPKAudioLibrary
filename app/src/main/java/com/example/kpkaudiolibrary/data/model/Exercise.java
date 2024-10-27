package com.example.kpkaudiolibrary.data.model;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise {
    public final HashMap<String, String> parts = new HashMap<>();

    public void addExerciseParts(File exerciseParts) {
        String partIndex = extractLastPart(exerciseParts.toString());

        if (!parts.containsKey(partIndex)) {
            parts.put(partIndex, exerciseParts.getPath());
        }
    }

    public String extractLastPart(String fileName) {
        // Регулярное выражение для поиска последней цифры или буквы после последнего подчеркивания
        Pattern pattern = Pattern.compile("_(\\w)(?=\\.wav$)");
        Matcher matcher = pattern.matcher(fileName);

        // Если нашли букву или цифру после последнего подчеркивания, возвращаем её
        if (matcher.find()) {
            return matcher.group(1);
        }

        // Если ничего не найдено, возвращаем "1"
        return "1";
    }
}
