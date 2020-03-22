package com.example.linetvtest.data.source;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;

import java.util.List;

public interface DramasDataSource {

    interface LoadDramasCallback {

        void onDramasLoaded(List<Drama> dramas);

        void onDataNotAvailable();
    }

    void getDramas(@NonNull LoadDramasCallback callback);

    void saveDramas(List<Drama> dramas);
}
