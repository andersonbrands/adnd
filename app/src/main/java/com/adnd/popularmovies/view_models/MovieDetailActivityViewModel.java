package com.adnd.popularmovies.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.popularmovies.models.FavoriteMovie;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.models.MovieVideo;
import com.adnd.popularmovies.repositories.MoviesRepository;

import java.util.List;

public class MovieDetailActivityViewModel extends AndroidViewModel {

    private static final String TAG = MovieDetailActivityViewModel.class.getSimpleName();

    private int movieId = -1;

    private MoviesRepository moviesRepository;

    private MediatorLiveData<Boolean> movieIsFavorite = new MediatorLiveData<>();

    private MediatorLiveData<List<MovieVideo>> movieVideosLiveData = new MediatorLiveData<>();

    public MovieDetailActivityViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = new MoviesRepository(application);
    }

    public void init(Movie movie) {
        if (movieId != -1) {
            return; // should only initialize once
        } else {
            movieId = movie.getId();
        }

        movieIsFavorite.addSource(moviesRepository.isFavorite(movieId), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                movieIsFavorite.setValue(aBoolean);
            }
        });

        movieVideosLiveData.addSource(moviesRepository.loadMovieVideos(movieId), new Observer<List<MovieVideo>>() {
            @Override
            public void onChanged(@Nullable List<MovieVideo> movieVideos) {
                movieVideosLiveData.setValue(movieVideos);
            }
        });

    }

    public MediatorLiveData<List<MovieVideo>> getMovieVideosLiveData() {
        return movieVideosLiveData;
    }

    public LiveData<Boolean> getMovieIsFavorite() {
        return movieIsFavorite;
    }

    public void toggleMovieIsFavorite(Movie movie, Boolean isFavorite) {
        if (isFavorite) {
            moviesRepository.deleteFavoriteMovie(new FavoriteMovie(movie.getId()));
        } else {
            moviesRepository.addToFavorites(movie);
        }
    }

}
