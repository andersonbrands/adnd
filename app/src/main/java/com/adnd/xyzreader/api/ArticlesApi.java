package com.adnd.xyzreader.api;

import com.adnd.xyzreader.models.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticlesApi {

    String BASE_URL = "https://nspf.github.io/";

    @GET("XYZReader/data.json")
    Call<List<Article>> getArticles();

}
