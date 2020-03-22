package com.example.linetvtest.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;
import com.example.linetvtest.data.source.DramasDataSource;
import com.example.linetvtest.data.source.DramasRepository;

import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private final DramasRepository mDramasRepository;

    private final MainContract.View mMainView;

    MainPresenter(@NonNull DramasRepository dramasRepository, @NonNull MainContract.View mainView){
        mDramasRepository = dramasRepository;
        mMainView = mainView;
    }

    @Override
    public void getTestData() {
        mDramasRepository.getDramas(new DramasDataSource.LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                for (Drama d : dramas){
                    Log.e("drama"+d.getId(),d.getTotalViews()+",");
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.e("drama","error");
            }
        });
    }

    @Override
    public void getSearchData(String keyword) {
        mDramasRepository.getSearchDramas(keyword, new DramasDataSource.LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                for (Drama d : dramas){
                    Log.e("drama*"+d.getId(),d.getTotalViews()+",");
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.e("drama*","error");
            }
        });
    }
}
