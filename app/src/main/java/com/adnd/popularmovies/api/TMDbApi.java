package com.adnd.popularmovies.api;

import com.adnd.popularmovies.BuildConfig;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.models.MovieReview;
import com.adnd.popularmovies.models.MovieVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TMDbApi {

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular?api_key=" + BuildConfig.TMDb_API_KEY)
    Call<List<Movie>> getPopularMovies();

    @GET("movie/top_rated?api_key=" + BuildConfig.TMDb_API_KEY)
    Call<List<Movie>> getTopRatedMovies();

    @GET("movie/{movieId}/videos?api_key=" + BuildConfig.TMDb_API_KEY)
    Call<List<MovieVideo>> getMovieVideos(@Path("movieId") int movieId);

    @GET("movie/{movieId}/reviews?api_key=" + BuildConfig.TMDb_API_KEY)
    Call<List<MovieReview>> getMovieReviews(@Path("movieId") int movieId);

}
