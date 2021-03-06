package com.example.linetvtest.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;
import com.example.linetvtest.data.source.DramasDataSource;
import com.example.linetvtest.data.source.DramasRepository;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private final DramasRepository mDramasRepository;

    private final MainContract.View mMainView;

    private boolean isSearchSuggestionUpdate = true;
    private boolean isSearchQuerySubmit = false;

    MainPresenter(@NonNull DramasRepository dramasRepository, @NonNull MainContract.View mainView){
        mDramasRepository = dramasRepository;
        mMainView = mainView;
    }

    @Override
    public void setSearchViewStatus() {
        if(!mDramasRepository.getSearchLastQuery().isEmpty()){
            mMainView.showSearchViewString(mDramasRepository.getSearchLastQuery());
        }
    }

    @Override
    public void getTestData() {
        mDramasRepository.getDramas(new DramasDataSource.LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                String lastQuery = mDramasRepository.getSearchLastQuery();
                if(lastQuery.isEmpty()){
                    mMainView.showDramaCards(dramas);
                }else {
                    mMainView.showSearchViewString(lastQuery);
                    isSearchQuerySubmit = true;
                    getSearchData(mDramasRepository.getSearchLastQuery(), false);
                }
            }

            @Override
            public void onDataNotAvailable(boolean isNetworkError) {
                mMainView.showDramaCardsError(isNetworkError ? DramaCardAdapter.VIEW_TYPE_ERROR_NETWORK : DramaCardAdapter.VIEW_TYPE_ERROR_SERVER);
            }
        });
    }

    @Override
    public void onQueryTextSubmit(String query) {
        getSearchData(query, false);
        mDramasRepository.saveSearchQuery(query);
        isSearchQuerySubmit = true;
    }

    @Override
    public void onQueryTextChange(String newText) {
        if(isSearchSuggestionUpdate){
            getSearchData(newText, !newText.isEmpty());
        }else {
            mMainView.showSearchSuggestions(null);
            isSearchSuggestionUpdate = true;
        }

        if(isSearchQuerySubmit){
            mDramasRepository.saveSearchQuery("");
            isSearchQuerySubmit = false;
        }
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
            public void onDataNotAvailable(boolean isNetworkError) {
                if(!isSearchSuggestion){
                    mMainView.showDramaCards(new ArrayList<Drama>());
                    mMainView.showDramaCardsError(DramaCardAdapter.VIEW_TYPE_ERROR_NO_RESULT);
                }
                mMainView.showSearchSuggestions(null);
            }
        });
    }

    @Override
    public void lockSearchSuggestionsUpdate() {
        isSearchSuggestionUpdate = false;
    }
}
