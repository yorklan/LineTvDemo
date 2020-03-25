package com.example.linetvtest.data.source.remote;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.linetvtest.data.Drama;
import com.example.linetvtest.data.source.DramasDataSource;

import java.util.List;

public class DramasRemoteDataSource implements DramasDataSource {

    private static DramasRemoteDataSource mInstance;
    private static RequestQueue mRequestQueue;

    private DramasRemoteDataSource(Context context){
        mRequestQueue = getRequestQueue(context);
    }

    public static synchronized DramasRemoteDataSource getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DramasRemoteDataSource(context.getApplicationContext());
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = com.android.volley.toolbox.Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    @Override
    public void getDramas(@NonNull final LoadDramasCallback callback) {
        String url = "https://static.linetv.tw/interview/dramas-sample.json";
        GsonRequest gsonRequest = new GsonRequest<>(Request.Method.GET, url, TestData.class, null, null,
                new Response.Listener<TestData>() {
                    @Override
                    public void onResponse(TestData response) {
                        callback.onDramasLoaded(response.getData());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onDataNotAvailable();
                    }
                });
        mRequestQueue.add(gsonRequest);
    }

    // not use : reserved space for data synchronization
    @Override
    public void saveDramas(List<Drama> dramas) {
    }

    // not use : reserved space for server api
    @Override
    public void getSearchDramas(String keyword, @NonNull LoadDramasCallback callback) {
    }

    // not use : reserved space for server data analysis
    @Override
    public void saveSearchQuery(String keyword) {
    }

    // not use
    @Override
    public String getSearchLastQuery() {
        return null;
    }
}
