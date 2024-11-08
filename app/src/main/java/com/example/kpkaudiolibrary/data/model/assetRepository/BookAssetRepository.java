package com.example.kpkaudiolibrary.data.model.assetRepository;

import com.example.kpkaudiolibrary.R;
import com.example.kpkaudiolibrary.data.model.BookTypes;
import com.example.kpkaudiolibrary.data.model.LanguageLevel;

import java.util.List;

public class BookAssetRepository {
    private final List<BookAsset> bookAssets = List.of(
            new BookAsset(LanguageLevel.A1, BookTypes.Book, R.mipmap.kpk_book_1),
            new BookAsset(LanguageLevel.A1, BookTypes.Workbook, R.mipmap.kpk_workbook_1),
            new BookAsset(LanguageLevel.A2, BookTypes.Book, R.mipmap.kpk_book_2),
            new BookAsset(LanguageLevel.A2, BookTypes.Workbook, R.mipmap.kpk_workbook_2)
            );

    public BookAsset getBookAsset(LanguageLevel languageLevel, BookTypes bookType) {
        for (BookAsset asset : bookAssets) {
            if (asset.languageLevel == languageLevel && asset.bookType == bookType) {
                return asset;
            }
        }
        return null;
    }




    public class BookAsset {
        private final LanguageLevel languageLevel;
        private final BookTypes bookType;
        private final int iconId;

        private BookAsset(LanguageLevel languageLevel, BookTypes bookType, int iconId) {
            this.languageLevel = languageLevel;
            this.bookType = bookType;
            this.iconId = iconId;
        }

        public int getIconId() {
            return iconId;
        }

        public BookTypes getBookType() {
            return bookType;
        }

        public LanguageLevel getLanguageLevel() {
            return languageLevel;
        }
    }
}
