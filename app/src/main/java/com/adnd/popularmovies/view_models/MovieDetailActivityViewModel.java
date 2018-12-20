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
import com.adnd.popularmovies.repositories.MoviesRepository;

public class MovieDetailActivityViewModel extends AndroidViewModel {

    private static final String TAG = MovieDetailActivityViewModel.class.getSimpleName();

    private MoviesRepository moviesRepository;

    private MediatorLiveData<Boolean> movieIsFavorite = new MediatorLiveData<>();

    public MovieDetailActivityViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = new MoviesRepository(application);
    }

    public void init(Movie movie) {
        movieIsFavorite.addSource(moviesRepository.isFavorite(movie.getId()), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                movieIsFavorite.setValue(aBoolean);
            }
        });
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
