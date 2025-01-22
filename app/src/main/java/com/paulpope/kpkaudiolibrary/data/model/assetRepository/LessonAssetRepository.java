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
                    new LessonAsset("Przedstawmy się"),
                    new LessonAsset("Dopełniacz jest wszędzie"),
                    new LessonAsset("Teatr żywych fotografii"),
                    new LessonAsset("Dwaj, trzej, czterej"),
                    new LessonAsset("Jacy oni są straszni!"),
                    new LessonAsset("Edukacja"),
                    new LessonAsset("Praca"),
                    new LessonAsset("Ja swoje wiem!"),
                    new LessonAsset("Nie zapomnij paszportu!"),
                    new LessonAsset("Kocham Cię Polsko!"),
                    new LessonAsset("Wejść czy wyjść?"),
                    new LessonAsset("Wjazd czy wyjazd?"),
                    new LessonAsset("Komu bije dzwon?"),
                    new LessonAsset("Zaduszki"),
                    new LessonAsset("Wesołych Świąt!"),
                    new LessonAsset("Przygody, przeżycia, wspomnienia"),
                    new LessonAsset("Trochę historii."),
                    new LessonAsset("Królestwo zwierząt"),
                    new LessonAsset("Zielono mi!"),
                    new LessonAsset("Rysopis Polaka konsumenta"),
                    new LessonAsset("Sztuka a piractwo"),
                    new LessonAsset("Muzeum? Dlaczego nie!"),
                    new LessonAsset("Czas na egzamin!"))
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