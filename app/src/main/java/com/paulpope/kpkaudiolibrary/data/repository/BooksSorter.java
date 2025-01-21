package com.paulpope.kpkaudiolibrary.data.repository;

import com.paulpope.kpkaudiolibrary.data.model.books.Book;
import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;

import java.util.ArrayList;
import java.util.List;

public class BooksSorter {
    private final List<BookRef> booksRefs = new ArrayList<>();
    public List<BookRef> getNotUploadedBooksRefs(List<BookRef> booksRefs, List<Book> books) {
        for (var bookRef : booksRefs) {
            boolean isUploaded = false;
            for(var book : books){
                if(bookRef.equals(book)){
                    isUploaded = true;
                    break;
                }
            }
            if(!isUploaded){
            this.booksRefs.add(bookRef);
            }
        }
        return this.booksRefs;
    }
}
