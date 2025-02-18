package com.paulpope.kpkaudiolibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.paulpope.kpkaudiolibrary.R;
import com.paulpope.kpkaudiolibrary.data.model.books.BookRef;
import com.paulpope.kpkaudiolibrary.data.repository.BookDownloader;
import com.paulpope.kpkaudiolibrary.data.repository.BookLibrary;
import com.paulpope.kpkaudiolibrary.data.model.books.LanguageLevel;
import com.paulpope.kpkaudiolibrary.data.repository.BooksSorter;
import com.paulpope.kpkaudiolibrary.data.repository.Deleter;
import com.paulpope.kpkaudiolibrary.data.repository.DownloadUiUpdateCallback;
import com.paulpope.kpkaudiolibrary.data.repository.FirebaseController;


import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TreeMap;


public class BookTableActivity extends BaseActivity {
    public final static String BOOK_KEY = "book";
    private BookLibrary bookLibrary;
    private final TreeMap<LanguageLevel, LinearLayout> booksLayouts = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseController firebaseController = new FirebaseController();

        bookLibrary = new BookLibrary(this);
        LinearLayout root = findViewById(R.id.layout_main);

        initializeHeaderOfActivity();
        createBooksPanels(root);

        firebaseController.getAllFireBaseBooks(bookRefs -> {
            BooksSorter booksSorter = new BooksSorter();
            List<BookRef> sortedBooksRefs = booksSorter.getNotUploadedBooksRefs(bookRefs, bookLibrary.getBooks());
            createBooksDownloadsPanels(root, sortedBooksRefs);
        });
    }

    private void initializeHeaderOfActivity() {
        FrameLayout languageButton = findViewById(R.id.language_button);
        languageButton.setOnClickListener(v -> {
            String currentLanguage = Locale.getDefault().getLanguage();
            if (currentLanguage.equals("en")) {
                changeLanguage("pl");
            } else {
                changeLanguage("en");
            }
        });

        ImageView languageIcon = findViewById(R.id.language_icon);
        languageIcon.setImageResource(Locale.getDefault().getLanguage().equals("en") ? R.drawable.ic_united_kingdom_flag : R.drawable.ic_poland_flag);
    }
    private void createBooksPanels(ViewGroup root) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (var book : bookLibrary.getBooks()) {
            View bookView;

            if (!booksLayouts.containsKey(book.getLanguageLevel())) {
                booksLayouts.put(book.getLanguageLevel(), createBooksLayout(root));
            }
            bookView = inflater.inflate(R.layout.book_item, booksLayouts.get(book.getLanguageLevel()), false);
            TextView bookName = bookView.findViewById(R.id.book_name);
            ImageView bookImage = bookView.findViewById(R.id.book_image);

            bookName.setText(getString(R.string.book_name, book.getLanguageLevel().name()));
            bookImage.setImageResource(book.getIconId());
            View deleteButton = bookView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(v -> {
                Deleter deleter = new Deleter();
                deleter.deleteDirectory(book.getFolder());
                bookLibrary.deleteBooksDataFile(this);
                recreate();
            });

            bookView.setOnClickListener(v -> {
                Intent intent = new Intent(this, LessonsTableActivity.class);
                intent.putExtra(BOOK_KEY, book);
                startActivity(intent);
            });

            Objects.requireNonNull(booksLayouts.get(book.getLanguageLevel())).addView(bookView);
        }
    }

    private void createBooksDownloadsPanels(ViewGroup root, Collection<BookRef> booksRefs){
        LayoutInflater inflater = LayoutInflater.from(this);

        for(var bookRef : booksRefs){
        View bookRefView;

        if (!booksLayouts.containsKey(bookRef.getLanguageLevel())) {
            booksLayouts.put(bookRef.getLanguageLevel(), createBooksLayout(root));
        }
        bookRefView = inflater.inflate(R.layout.not_uploaded_book_item, booksLayouts.get(bookRef.getLanguageLevel()), false);
            TextView bookName = bookRefView.findViewById(R.id.book_name);
            ImageView bookImage = bookRefView.findViewById(R.id.book_image);
            View downloadButton = bookRefView.findViewById(R.id.download_button);

            bookName.setText(getString(R.string.book_name, bookRef.getLanguageLevel().name()));
            bookImage.setImageResource(bookRef.getIconId());
            ProgressBar horizontalProgressBar = bookRefView.findViewById(R.id.horizontalProgressBar);

            DownloadUiUpdateCallback updateCallback = progress ->
                    runOnUiThread(() -> {
                        horizontalProgressBar.setVisibility(View.VISIBLE);
                        horizontalProgressBar.setProgress(progress);
                        downloadButton.setVisibility(View.GONE);
                        if (progress == 100) {
                            horizontalProgressBar.setVisibility(View.GONE);
                        }
                    });

            downloadButton.setOnClickListener(v -> {
                BookDownloader bookDownloader = new BookDownloader();
                bookDownloader.downloadBooks(this, bookRef, updateCallback, () -> {
                        bookLibrary.deleteBooksDataFile(this);
                        recreate();
                });
            });
            Objects.requireNonNull(booksLayouts.get(bookRef.getLanguageLevel())).addView(bookRefView);
        }
    }

    private LinearLayout createBooksLayout(ViewGroup root) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout booksLayout = new LinearLayout(this);
        booksLayout.setOrientation(LinearLayout.HORIZONTAL);
        booksLayout.setBaselineAligned(false);
        root.addView(booksLayout, layoutParams);
        return booksLayout;
    }

    private void changeLanguage(String languageCode) {
        getSharedPreferences("app_preferences", MODE_PRIVATE).edit().putString("language", languageCode).apply();

        Intent intent = new Intent(this, BookTableActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}