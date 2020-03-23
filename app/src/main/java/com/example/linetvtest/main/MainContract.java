package com.example.linetvtest.main;

import com.example.linetvtest.data.Drama;

import java.util.List;

public interface MainContract {

    interface View {

        void showDramaCards(List<Drama> dramaList);
    }

    interface Presenter {

        void getTestData();

        void getSearchData(String keyword);
    }
}
