package com.adnd.xyzreader.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.xyzreader.models.Article;
import com.adnd.xyzreader.repositories.ArticleRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MediatorLiveData<List<Article>> articleListLiveData = new MediatorLiveData<>();

    private ArticleRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
        loadArticleList();
    }

    public LiveData<List<Article>> getArticleListLiveData() {
        return articleListLiveData;
    }

    public void loadArticleList() {
        final LiveData<List<Article>> source = repository.loadAllArticles();

        articleListLiveData.addSource(source, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles == null) {
                    // TODO handle error
                } else if (articles.size() == 0) {
                    // TODO handle empty
                } else {
                    // TODO handle success
                }
                articleListLiveData.setValue(articles);
            }
        });

    }
}
