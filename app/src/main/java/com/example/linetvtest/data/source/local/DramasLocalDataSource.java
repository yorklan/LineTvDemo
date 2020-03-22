package com.example.linetvtest.data.source.local;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.Drama;
import com.example.linetvtest.data.source.DramasDataSource;
import com.example.linetvtest.executors.GlobalExecutors;

import java.util.List;

public class DramasLocalDataSource implements DramasDataSource {

    private static volatile DramasLocalDataSource mInstance;

    private DramasDao mDramasDao;

    private GlobalExecutors mGlobalExecutors;

    private DramasLocalDataSource(@NonNull GlobalExecutors globalExecutors,
                                 @NonNull DramasDao dramasDao) {
        mGlobalExecutors = globalExecutors;
        mDramasDao = dramasDao;
    }

    public static DramasLocalDataSource getInstance(@NonNull GlobalExecutors globalExecutors,
                                                   @NonNull DramasDao dramasDao) {
        if (mInstance == null) {
            synchronized (DramasLocalDataSource.class) {
                if (mInstance == null) {
                    mInstance = new DramasLocalDataSource(globalExecutors, dramasDao);
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
}
