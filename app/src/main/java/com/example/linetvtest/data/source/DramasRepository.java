package com.example.linetvtest.data.source;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;

import java.util.List;

public class DramasRepository implements DramasDataSource {

    private static DramasRepository mInstance = null;

    private final DramasDataSource mDramasRemoteDataSource;

    private final DramasDataSource mDramasLocalDataSource;

    private DramasRepository(@NonNull DramasDataSource dramasRemoteDataSource,
                                  @NonNull DramasDataSource dramasLocalDataSource){
        mDramasRemoteDataSource = dramasRemoteDataSource;
        mDramasLocalDataSource = dramasLocalDataSource;
    }

    public static DramasRepository getInstance(DramasDataSource dramasRemoteDataSource,
                                              DramasDataSource dramasLocalDataSource) {
        if (mInstance == null) {
            mInstance = new DramasRepository(dramasRemoteDataSource, dramasLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public void getDramas(@NonNull final LoadDramasCallback callback) {
        mDramasRemoteDataSource.getDramas(new LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                callback.onDramasLoaded(dramas);
                saveDramas(dramas);
            }

            @Override
            public void onDataNotAvailable(boolean isNetworkError) {
                getDramasFromLocalDataSource(callback, isNetworkError);
            }
        });
    }

    private void getDramasFromLocalDataSource(@NonNull final LoadDramasCallback callback, final boolean isShowNetWorkError){
        mDramasLocalDataSource.getDramas(new LoadDramasCallback() {
            @Override
            public void onDramasLoaded(List<Drama> dramas) {
                callback.onDramasLoaded(dramas);
            }

            @Override
            public void onDataNotAvailable(boolean isNetworkError) {
                callback.onDataNotAvailable(isShowNetWorkError);
            }
        });
    }

    @Override
    public void saveDramas(List<Drama> dramas) {
        mDramasLocalDataSource.saveDramas(dramas);
    }

    @Override
    public void getSearchDramas(String keyword, @NonNull LoadDramasCallback callback) {
        mDramasLocalDataSource.getSearchDramas("%"+keyword+"%", callback);
    }

    @Override
    public void saveSearchQuery(String keyword) {
        mDramasLocalDataSource.saveSearchQuery(keyword);
    }

    @Override
    public String getSearchLastQuery() {
        return mDramasLocalDataSource.getSearchLastQuery();
    }
}
