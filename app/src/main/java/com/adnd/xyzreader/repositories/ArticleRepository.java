package com.adnd.xyzreader.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.adnd.xyzreader.database.AppDatabase;
import com.adnd.xyzreader.database.ArticleDao;
import com.adnd.xyzreader.models.Article;

import java.util.List;

public class ArticleRepository {

    private ArticleDao articleDao;

    public ArticleRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.articleDao = db.articleDao();
    }

    public LiveData<Article> loadArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public LiveData<List<Article>> loadAllArticles() {
        return articleDao.getAllArticles();
    }

}
