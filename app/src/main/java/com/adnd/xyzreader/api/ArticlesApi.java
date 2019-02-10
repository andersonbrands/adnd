package com.adnd.xyzreader.api;

import com.adnd.xyzreader.models.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticlesApi {

    String BASE_URL = "https://go.udacity.com/";

    @GET("xyz-reader-json")
    Call<List<Article>> getArticles();

}
