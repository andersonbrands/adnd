package com.adnd.popularmovies.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.repositories.MoviesRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    private MutableLiveData<List<Movie>> moviesListLiveData = new MutableLiveData<>();

    private LiveData<List<Movie>> favoriteMoviesLiveData = null;
    private Observer<List<Movie>> favoriteMoviesObserver = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            moviesListLiveData.setValue(movies);
        }
    };

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);

        loadPopularMovies();
    }

    public LiveData<List<Movie>> getMoviesListLiveData() {
        return moviesListLiveData;
    }

    public void loadPopularMovies() {
        loadPopularOrTopRatedMovies(MoviesRepository.POPULAR_MOVIES_KEY);
//        if (favoriteMoviesLiveData != null) {
//            favoriteMoviesLiveData.removeObserver(favoriteMoviesObserver);
//        }
//
//        final LiveData<List<Movie>> ld = moviesRepository.loadPopularMovies();
//        ld.observeForever(new Observer<List<Movie>>() {
//            @Override
//            public void onChanged(@Nullable List<Movie> movies) {
//                moviesListLiveData.setValue(movies);
//                ld.removeObserver(this);
//            }
//        });
    }

    public void loadTopRatedMovies() {
        loadPopularOrTopRatedMovies(MoviesRepository.TOP_RATED_MOVIES_KEY);
//        if (favoriteMoviesLiveData != null) {
//            favoriteMoviesLiveData.removeObserver(favoriteMoviesObserver);
//        }
//
//        final LiveData<List<Movie>> ld = moviesRepository.loadTopRatedMovies();
//        ld.observeForever(new Observer<List<Movie>>() {
//            @Override
//            public void onChanged(@Nullable List<Movie> movies) {
//                moviesListLiveData.setValue(movies);
//                ld.removeObserver(this);
//            }
//        });
    }

    private void loadPopularOrTopRatedMovies(String key) {
        if (favoriteMoviesLiveData != null) {
            favoriteMoviesLiveData.removeObserver(favoriteMoviesObserver);
        }

        final LiveData<List<Movie>> popularOrTopRatedMoviesLiveData =
                (key.equals(MoviesRepository.POPULAR_MOVIES_KEY)) ?
                        moviesRepository.loadPopularMovies() :
                        moviesRepository.loadTopRatedMovies();

        popularOrTopRatedMoviesLiveData.observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                moviesListLiveData.setValue(movies);
                popularOrTopRatedMoviesLiveData.removeObserver(this);
            }
        });
    }

    public void loadFavoriteMovies() {
        favoriteMoviesLiveData = moviesRepository.loadFavoriteMovies();
        favoriteMoviesLiveData.observeForever(favoriteMoviesObserver);
    }

}
