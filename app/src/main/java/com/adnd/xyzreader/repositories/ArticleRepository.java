package com.adnd.xyzreader.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.adnd.xyzreader.AppExecutors;
import com.adnd.xyzreader.api.ArticlesApi;
import com.adnd.xyzreader.api.ArticlesListConverterFactory;
import com.adnd.xyzreader.database.AppDatabase;
import com.adnd.xyzreader.database.ArticleDao;
import com.adnd.xyzreader.models.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        MutableLiveData<List<Article>> articleListLiveData = new MutableLiveData<>();
        loadArticlesFromWebService(articleListLiveData);
        return articleListLiveData;
    }

    private void loadArticlesFromWebService(@NonNull final MutableLiveData<List<Article>> articlesListLiveData) {
        Retrofit fit = new Retrofit.Builder()
                .baseUrl(ArticlesApi.BASE_URL)
                .addConverterFactory(new ArticlesListConverterFactory())
                .build();

        ArticlesApi api = fit.create(ArticlesApi.class);

        Call<List<Article>> articlesCall = api.getArticles();

        articlesCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articlesListLiveData.setValue(response.body());
                    insertArticlesToDb(response.body());
                } else {
                    onArticlesCallError();
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                onArticlesCallError();
            }

            private void onArticlesCallError() {
                articlesListLiveData.setValue(null);
            }
        });
    }

    private void insertArticlesToDb(final List<Article> articles) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                articleDao.insert(articles);
            }
        });
    }

}
