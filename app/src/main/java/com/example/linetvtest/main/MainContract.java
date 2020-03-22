package com.example.linetvtest.main;

public interface MainContract {

    interface View {

    }

    interface Presenter {

        void getTestData();

        void getSearchData(String keyword);
    }
}
