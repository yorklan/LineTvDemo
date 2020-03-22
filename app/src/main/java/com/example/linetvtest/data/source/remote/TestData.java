package com.example.linetvtest.data.source.remote;

import com.example.linetvtest.data.Drama;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestData {

    @SerializedName("data")
    private List<Drama> data = null;

    public List<Drama> getData() {
        return data;
    }
}
