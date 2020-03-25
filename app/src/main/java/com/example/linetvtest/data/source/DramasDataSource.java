package com.example.linetvtest.data.source;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;

import java.util.List;

public interface DramasDataSource {

    interface LoadDramasCallback {

        void onDramasLoaded(List<Drama> dramas);

        void onDataNotAvailable(boolean isNetworkError);
    }

    void getDramas(@NonNull LoadDramasCallback callback);

    void saveDramas(List<Drama> dramas);

    void getSearchDramas(String keyword, @NonNull LoadDramasCallback callback);

    void saveSearchQuery(String keyword);

    String getSearchLastQuery();
}
