package com.paulpope.kpkaudiolibrary.data.model.assetRepository;

import com.paulpope.kpkaudiolibrary.data.model.books.LanguageLevel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LessonAssetRepository {
    private final Map<LanguageLevel, List<LessonAsset>> lessons = Map.of(
            LanguageLevel.A1, List.of(
                    new LessonAsset("Pierwszy dzień w szkole"),
                    new LessonAsset("Cześć, skąd jesteś?"),
                    new LessonAsset("Mami, kto to jest?"),
                    new LessonAsset("Jaki jesteś?"),
                    new LessonAsset("Jesteś instruktorem tanga?"),
                    new LessonAsset("Co robisz? Nudzę się!"),
                    new LessonAsset("Małe zakupy"),
                    new LessonAsset("Mami, jesteś głodna?"),
                    new LessonAsset("Lubisz marchewkę?"),
                    new LessonAsset("Uwielbiam polskie jedzenie!"),
                    new LessonAsset("Rodzina"),
                    new LessonAsset("Co robisz w poniedziałek o ósmej?"),
                    new LessonAsset("Gdzie byłaś Mami? Byłam w kinie."),
                    new LessonAsset("Z przewodnikiem po Krakowie"),
                    new LessonAsset("Karton czy pudełko?"),
                    new LessonAsset("Co ma być, to będzie"),
                    new LessonAsset("Plotki, plotki. Kto z kim i o czym?"),
                    new LessonAsset("Pokaż mi swoje mieszkanie..."),
                    new LessonAsset("Wszędzie dobrze, ale w domu najlepiej."),
                    new LessonAsset("Podróże kształcą"),
                    new LessonAsset("Kiedy to było?"),
                    new LessonAsset("Dokąd pojedziemy na weekend?"),
                    new LessonAsset("Za małe? Za duże? W sam raz!"),
                    new LessonAsset("Jak cię widzą, tak cię piszą."),
                    new LessonAsset("Ani ręką, ani nogą..."),
                    new LessonAsset("Same problemy!")
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

    public LessonAsset getLessonAsset(LanguageLevel languageLevel, int lessonNumber) {
        return Objects.requireNonNull(lessons.get(languageLevel)).get(lessonNumber - 1);
    }

    public static class LessonAsset {
        private final String name;

        public LessonAsset(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}