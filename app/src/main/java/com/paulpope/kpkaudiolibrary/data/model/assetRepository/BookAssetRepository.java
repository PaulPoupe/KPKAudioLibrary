package com.paulpope.kpkaudiolibrary.data.model.assetRepository;

import com.paulpope.kpkaudiolibrary.R;
import com.paulpope.kpkaudiolibrary.data.model.books.BookTypes;
import com.paulpope.kpkaudiolibrary.data.model.books.LanguageLevel;

import java.util.List;

public class BookAssetRepository {
    private final List<BookAsset> bookAssets = List.of(
            new BookAsset(LanguageLevel.A1, BookTypes.Textbook, R.mipmap.kpk_book_1),
            new BookAsset(LanguageLevel.A1, BookTypes.Workbook, R.mipmap.kpk_workbook_1),
            new BookAsset(LanguageLevel.A2, BookTypes.Textbook, R.mipmap.kpk_book_2),
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

    public static class BookAsset {
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
    }
}
