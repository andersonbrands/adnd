package com.adnd.xyzreader.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.xyzreader.models.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles")
    LiveData<List<Article>>
    getAllArticles();

    @Query("SELECT * FROM articles WHERE id=:id")
    LiveData<Article> getArticleById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Article> article);
}
