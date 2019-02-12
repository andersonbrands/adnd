package com.adnd.xyzreader;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.adnd.xyzreader.adapters.ArticleAdapter;
import com.adnd.xyzreader.adapters.ListItemClickListener;
import com.adnd.xyzreader.databinding.ActivityMainBinding;
import com.adnd.xyzreader.models.Article;
import com.adnd.xyzreader.view_models.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener<Article> {

    private ActivityMainBinding binding;
    private MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        model.getArticleListLiveData().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                setRecyclerView(articles);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.loadArticleList();
            }
        });

    }

    private void setRecyclerView(List<Article> articles) {
        int columnCount = getResources().getInteger(R.integer.main_list_column_count);
        binding.rvArticles.setLayoutManager(new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));
        ArticleAdapter adapter = new ArticleAdapter(articles, this);
        binding.rvArticles.setAdapter(adapter);
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onListItemClick(Article clickedItem, int position) {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra(ArticleDetailsActivity.ARTICLE_ID_EXTRA_KEY, clickedItem.getId());
        startActivity(intent);
    }
}
