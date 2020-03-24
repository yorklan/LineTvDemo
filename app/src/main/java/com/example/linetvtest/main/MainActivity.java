package com.example.linetvtest.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private SearchSuggestionCursorAdapter mSearchSuggestionCursorAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenter(Injection.provideDramasRepository(this), this);
        buildSearchView();
        buildRecyclerView();
        mMainPresenter.getTestData();
    }

    @SuppressLint("CutPasteId")
    private void buildSearchView() {
        mSearchView = findViewById(R.id.search_main);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMainPresenter.onQueryTextSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMainPresenter.onQueryTextChange(newText);
                return false;
            }
        });

        SearchView.SearchAutoComplete mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setThreshold(1);
        mSearchSuggestionCursorAdapter = new SearchSuggestionCursorAdapter(this,null);
        mSearchView.setSuggestionsAdapter(mSearchSuggestionCursorAdapter);
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
    public void showSearchSuggestions(List<Drama> dramaList) {
        if(dramaList==null){
            mSearchSuggestionCursorAdapter.swapCursor(null);
        }else {
            mSearchSuggestionCursorAdapter.swapCursor(buildCursor(dramaList));
        }
        mSearchSuggestionCursorAdapter.notifyDataSetChanged();
    }

    private Cursor buildCursor(List<Drama> dramaList){
        String[] columns = new String[] { "_id", "name"};
        MatrixCursor matrixCursor = new MatrixCursor(columns);
        for ( Drama drama : dramaList ) {
            matrixCursor.newRow()
                    .add("_id", drama.getId())
                    .add("name", drama.getName());
        }
        return matrixCursor;
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

    public class SearchSuggestionCursorAdapter extends CursorAdapter {

        private LayoutInflater mLayoutInflater;

        SearchSuggestionCursorAdapter(Context context, Cursor c) {
            super(context, c, false);
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return mLayoutInflater.inflate(R.layout.card_search, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView textHint = view.findViewById(R.id.text_card_search_hint);
            textHint.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textHintTarget = v.findViewById(R.id.text_card_search_hint);
                    mMainPresenter.lockSearchSuggestionsUpdate();
                    mSearchView.setQuery(textHintTarget.getText().toString(), true);
                }
            });
        }
    }
}
