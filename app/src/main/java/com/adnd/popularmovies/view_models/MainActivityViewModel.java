package com.adnd.popularmovies.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.adnd.popularmovies.models.Movie;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> moviesListLiveData = new MutableLiveData<>();

    public LiveData<List<Movie>> getMoviesListLiveData() {
        return moviesListLiveData;
    }

    public void setMoviesListLiveDataValue(List<Movie> moviesList) {
        moviesListLiveData.setValue(moviesList);
    }

}
