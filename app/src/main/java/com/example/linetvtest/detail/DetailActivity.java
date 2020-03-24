package com.example.linetvtest.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.linetvtest.R;
import com.example.linetvtest.data.Drama;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Drama drama = getIntent().getParcelableExtra("drama");
        if(drama!=null){
            ImageView imgThumb = findViewById(R.id.img_detail_thumb);
            Glide.with(this)
                    .load(drama.getThumb())
                    .transition(withCrossFade())
                    .into(imgThumb);
            TextView textCreateAt = findViewById(R.id.text_detail_create_at);
            textCreateAt.setText(drama.getCreatedAtString());
            TextView textTotalViews = findViewById(R.id.text_detail_total_view);
            textTotalViews.setText(drama.getTotalViewsString());
            TextView textRating =  findViewById(R.id.text_detail_rating);
            textRating.setText(drama.getRatingString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_right);
    }
}
