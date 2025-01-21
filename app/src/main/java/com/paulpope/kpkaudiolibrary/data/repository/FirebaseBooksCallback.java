package com.paulpope.kpkaudiolibrary.data.repository;

import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;

import java.util.List;

public interface FirebaseBooksCallback {
    void onBooksLoaded(List<BookRef> booksRefs);
}

