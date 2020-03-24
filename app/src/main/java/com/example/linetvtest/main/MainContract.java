package com.example.linetvtest.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.linetvtest.data.Drama;

import java.util.List;

public interface MainContract {

    interface View {

        void showSearchSuggestions(@Nullable List<Drama> dramaList);

        void showDramaCards(@Nullable List<Drama> dramaList);
    }

    interface Presenter {

        void getTestData();

        void onQueryTextSubmit(String query);

        void onQueryTextChange(String newText);

        void lockSearchSuggestionsUpdate();
    }
}
