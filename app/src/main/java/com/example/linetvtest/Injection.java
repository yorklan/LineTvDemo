package com.example.linetvtest;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.linetvtest.data.source.DramasRepository;
import com.example.linetvtest.data.source.local.DramasLocalDataSource;
import com.example.linetvtest.data.source.local.LinetvTestDatabase;
import com.example.linetvtest.data.source.remote.DramasRemoteDataSource;
import com.example.linetvtest.executors.GlobalExecutors;

public class Injection {

    public static DramasRepository provideDramasRepository(@NonNull Context context) {
        LinetvTestDatabase database = LinetvTestDatabase.getInstance(context);
        return DramasRepository.getInstance(
                DramasRemoteDataSource.getInstance(context),
                DramasLocalDataSource.getInstance(new GlobalExecutors(), database.dramasDao(), context.getSharedPreferences("LineTv.Search", Context.MODE_PRIVATE)));
    }
}
