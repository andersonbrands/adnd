package com.adnd.popularmovies.api;

import com.adnd.popularmovies.BuildConfig;
import com.adnd.popularmovies.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TMDbApi {

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular?api_key=" + BuildConfig.TMDb_API_KEY)
    Call<List<Movie>> getPopularMovies();

    @GET("movie/top_rated?api_key=" + BuildConfig.TMDb_API_KEY)
    Call<List<Movie>> getTopRatedMovies();

}
