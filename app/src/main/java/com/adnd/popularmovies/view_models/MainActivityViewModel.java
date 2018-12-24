package com.adnd.popularmovies.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.popularmovies.R;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.repositories.MoviesRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    private MutableLiveData<List<Movie>> moviesListLiveData = new MutableLiveData<>();

    private final ObservableInt emptyListTestResId = new ObservableInt();

    private LiveData<List<Movie>> favoriteMoviesLiveData = null;
    private Observer<List<Movie>> favoriteMoviesObserver = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies == null || movies.size() == 0) {
                emptyListTestResId.set(R.string.you_have_no_favorite_movie);
            }
            moviesListLiveData.setValue(movies);
        }
    };

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);

        emptyListTestResId.set(R.string.empty_list);

        loadPopularMovies();
    }

    public ObservableInt getEmptyListTestResId() {
        return emptyListTestResId;
    }

    public LiveData<List<Movie>> getMoviesListLiveData() {
        return moviesListLiveData;
    }

    public void loadPopularMovies() {
        loadPopularOrTopRatedMovies(MoviesRepository.POPULAR_MOVIES_KEY);
    }

    public void loadTopRatedMovies() {
        loadPopularOrTopRatedMovies(MoviesRepository.TOP_RATED_MOVIES_KEY);
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
                if (movies == null) {
                    emptyListTestResId.set(R.string.err_something_wrong_try_again);
                }
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
