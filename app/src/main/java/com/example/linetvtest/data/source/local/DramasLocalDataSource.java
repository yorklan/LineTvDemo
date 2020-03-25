package com.example.linetvtest.data.source.local;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;
import com.example.linetvtest.data.source.DramasDataSource;
import com.example.linetvtest.executors.GlobalExecutors;

import java.util.List;

public class DramasLocalDataSource implements DramasDataSource {

    private static volatile DramasLocalDataSource mInstance;

    private DramasDao mDramasDao;

    private GlobalExecutors mGlobalExecutors;

    private SharedPreferences mSharedPreferencesSearch;

    private final String searchLastQuery = "searchLastQuery";

    private DramasLocalDataSource(@NonNull GlobalExecutors globalExecutors,
                                  @NonNull DramasDao dramasDao,
                                  @NonNull SharedPreferences sharedPreferencesSearch) {
        mGlobalExecutors = globalExecutors;
        mDramasDao = dramasDao;
        mSharedPreferencesSearch = sharedPreferencesSearch;
    }

    public static DramasLocalDataSource getInstance(@NonNull GlobalExecutors globalExecutors,
                                                    @NonNull DramasDao dramasDao,
                                                    @NonNull SharedPreferences sharedPreferencesSearch) {
        if (mInstance == null) {
            synchronized (DramasLocalDataSource.class) {
                if (mInstance == null) {
                    mInstance = new DramasLocalDataSource(globalExecutors, dramasDao, sharedPreferencesSearch);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void getDramas(@NonNull final LoadDramasCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Drama> dramas = mDramasDao.getDramas();
                mGlobalExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (dramas.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onDramasLoaded(dramas);
                        }
                    }
                });
            }
        };

        mGlobalExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveDramas(final List<Drama> dramas) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mDramasDao.insertAllDramas(dramas);
            }
        };
        mGlobalExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void getSearchDramas(final String keyword, @NonNull final LoadDramasCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Drama> dramas = mDramasDao.getSearchDramas(keyword);
                mGlobalExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (dramas!=null && !dramas.isEmpty()) {
                            callback.onDramasLoaded(dramas);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mGlobalExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveSearchQuery(String keyword) {
        mSharedPreferencesSearch.edit()
                .putString(searchLastQuery, keyword)
                .apply();
    }

    @Override
    public String getSearchLastQuery() {
        return mSharedPreferencesSearch.getString(searchLastQuery, "");
    }
}
