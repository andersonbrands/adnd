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

public class ArticleDetailsViewModel extends AndroidViewModel {

    private MediatorLiveData<Article> articleLiveData = new MediatorLiveData<>();
    private ArticleRepository repository;
    private int articleId;

    public ArticleDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
    }

    public LiveData<Article> getArticleLiveData() {
        return articleLiveData;
    }

    public void setArticleId(int articleId) {
        if (this.articleId != articleId) {
            this.articleId = articleId;

            LiveData<Article> source = repository.loadArticleById(articleId);

            articleLiveData.addSource(source, new Observer<Article>() {
                @Override
                public void onChanged(@Nullable Article article) {
                    articleLiveData.setValue(article);
                }
            });
        }
    }
}
