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

    private boolean isSearchSuggestionUpdate = true;

    MainPresenter(@NonNull DramasRepository dramasRepository, @NonNull MainContract.View mainView){
        mDramasRepository = dramasRepository;
        mMainView = mainView;
    }

    @Override
    public void getTestData() {
        mDramasRepository.getDramas(new DramasDataSource.LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                mMainView.showDramaCards(dramas);
            }

            @Override
            public void onDataNotAvailable() {
                Log.e("drama","error");
            }
        });
    }

    @Override
    public void onQueryTextSubmit(String query) {
        getSearchData(query, false);
    }

    @Override
    public void onQueryTextChange(String newText) {
        if(isSearchSuggestionUpdate){
            getSearchData(newText, !newText.isEmpty());
        }else {
            mMainView.showSearchSuggestions(null);
        }
        isSearchSuggestionUpdate = true;
    }

    private void getSearchData(String keyword, final boolean isSearchSuggestion) {
        mDramasRepository.getSearchDramas(keyword, new DramasDataSource.LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                if(isSearchSuggestion){
                    mMainView.showSearchSuggestions(dramas);
                }else {
                    mMainView.showDramaCards(dramas);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mMainView.showSearchSuggestions(null);
            }
        });
    }

    @Override
    public void lockSearchSuggestionsUpdate() {
        isSearchSuggestionUpdate = false;
    }
}
