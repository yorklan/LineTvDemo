package com.example.linetvtest.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.linetvtest.Injection;
import com.example.linetvtest.R;
import com.example.linetvtest.data.Drama;
import com.example.linetvtest.detail.DetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainPresenter mMainPresenter;
    private DramaCardAdapter mDramaCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenter(Injection.provideDramasRepository(this), this);
        buildSearchView();
        buildRecyclerView();
        mMainPresenter.getTestData();
    }

    private void buildSearchView() {
        SearchView searchView = findViewById(R.id.search_main);
        EditText editTextSearch = searchView.findViewById(R.id.search_src_text);
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // FIXME
                    Log.e("search","1");
                }
                return false;
            }
        });
    }

    private void buildRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mDramaCardAdapter = new DramaCardAdapter();
        recyclerView.setAdapter(mDramaCardAdapter);
    }

    @Override
    public void showDramaCards(List<Drama> dramaList) {
        mDramaCardAdapter.updateData(dramaList);
        mDramaCardAdapter.setOnItemClickListener(new DramaCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Drama drama) {
                startActivityDetail(drama);
            }
        });
        mDramaCardAdapter.notifyDataSetChanged();
    }

    private void startActivityDetail(@NonNull Drama drama){
        Intent intent  = new Intent(this, DetailActivity.class);
        intent.putExtra("drama",drama);
        startActivity(intent);
    }
}
