package com.example.kpkaudiolibrary.data.model.assetRepository;

import com.example.kpkaudiolibrary.data.model.LanguageLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LessonAssetRepository {
    private final Map<LanguageLevel ,List<LessonAsset>> lessons = Map.of(
            LanguageLevel.A1, List.of(
                    new LessonAsset("lesson 1"),
                    new LessonAsset("lesson 2"),
                    new LessonAsset("lesson 3"),
                    new LessonAsset("lesson 4"),
                    new LessonAsset("lesson 5"),
                    new LessonAsset("lesson 6"),
                    new LessonAsset("lesson 7"),
                    new LessonAsset("lesson 8"),
                    new LessonAsset("lesson 9"),
                    new LessonAsset("lesson 10")
            ),
            LanguageLevel.A2, List.of(
                    new LessonAsset("lesson 1"),
                    new LessonAsset("lesson 2"),
                    new LessonAsset("lesson 3"),
                    new LessonAsset("lesson 4"),
                    new LessonAsset("lesson 5"),
                    new LessonAsset("lesson 6"),
                    new LessonAsset("lesson 7"),
                    new LessonAsset("lesson 8"),
                    new LessonAsset("lesson 9"),
                    new LessonAsset("lesson 10"))
    );

    public LessonAsset getLessonAsset(LanguageLevel languageLevel, int lessonNumber){
        return Objects.requireNonNull(lessons.get(languageLevel)).get(lessonNumber -1);
    }

    public class LessonAsset{
        private final String name;

        public LessonAsset(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }
}