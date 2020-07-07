package com.grab.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.grab.news.ui.feature.news.NewsListFragment;

import butterknife.ButterKnife;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_activity);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new NewsListFragment()).commit();
    }
}