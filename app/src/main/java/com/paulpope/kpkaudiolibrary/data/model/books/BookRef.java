package com.paulpope.kpkaudiolibrary.data.model.books;

import com.google.firebase.storage.StorageReference;
import com.paulpope.kpkaudiolibrary.data.model.assetRepository.BookAssetRepository;

import java.io.IOException;

public class BookRef {
    private final LanguageLevel languageLevel;
    private final BookTypes bookType;
    private final int iconId;
    private final StorageReference ref;

    public BookRef(String folderName, BookTypes bookType, StorageReference ref){
        this.bookType = bookType;
        this.ref = ref;
        languageLevel = LanguageLevel.valueOf(folderName);

        BookAssetRepository assetRepository = new BookAssetRepository();
        iconId = assetRepository.getBookAsset(languageLevel, bookType).getIconId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj.getClass() == this.getClass() || obj instanceof Book)) {
            return false;
        }

        if (obj.getClass() == this.getClass()){
            BookRef otherBookRef = (BookRef) obj;

            return otherBookRef.languageLevel == this.languageLevel && otherBookRef.bookType == this.bookType;
        }else{
            Book otherBook = (Book) obj;
            return otherBook.getLanguageLevel().equals(this.languageLevel)  && otherBook.getBookType().equals(this.bookType);
        }
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
