package com.example.linetvtest.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.linetvtest.Injection;
import com.example.linetvtest.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(Injection.provideDramasRepository(this), this);
    }
}
