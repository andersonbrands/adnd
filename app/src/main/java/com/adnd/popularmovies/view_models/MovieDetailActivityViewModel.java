package com.adnd.popularmovies.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.popularmovies.R;
import com.adnd.popularmovies.models.FavoriteMovie;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.models.MovieReview;
import com.adnd.popularmovies.models.MovieVideo;
import com.adnd.popularmovies.repositories.MoviesRepository;

import java.util.List;

public class MovieDetailActivityViewModel extends AndroidViewModel {

    private static final String TAG = MovieDetailActivityViewModel.class.getSimpleName();

    private int movieId = -1;

    private final ObservableInt emptyVideosListTextResId = new ObservableInt();
    private final ObservableInt emptyReviewsListTextResId = new ObservableInt();

    private MoviesRepository moviesRepository;

    private MediatorLiveData<Boolean> movieIsFavorite = new MediatorLiveData<>();

    private MediatorLiveData<List<MovieVideo>> movieVideosLiveData = new MediatorLiveData<>();

    private MediatorLiveData<List<MovieReview>> movieReviewsLiveData = new MediatorLiveData<>();

    public MovieDetailActivityViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = new MoviesRepository(application);
        emptyVideosListTextResId.set(R.string.empty_list);
        emptyReviewsListTextResId.set(R.string.empty_list);
    }

    public ObservableInt getEmptyVideosListTextResId() {
        return emptyVideosListTextResId;
    }

    public ObservableInt getEmptyReviewsListTextResId() {
        return emptyReviewsListTextResId;
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
                if (movieVideos == null) {
                    emptyVideosListTextResId.set(R.string.err_load_trailers);
                } else if (movieVideos.size() == 0) {
                    emptyVideosListTextResId.set(R.string.no_trailers_for_movie);
                }
                movieVideosLiveData.setValue(movieVideos);
            }
        });

        movieReviewsLiveData.addSource(moviesRepository.loadMovieReviews(movieId), new Observer<List<MovieReview>>() {
            @Override
            public void onChanged(@Nullable List<MovieReview> movieReviews) {
                if (movieReviews == null) {
                    emptyReviewsListTextResId.set(R.string.err_load_reviews);
                } else if (movieReviews.size() == 0) {
                    emptyReviewsListTextResId.set(R.string.no_reviews_for_movie);
                }
                movieReviewsLiveData.setValue(movieReviews);
            }
        });
    }

    public MediatorLiveData<List<MovieVideo>> getMovieVideosLiveData() {
        return movieVideosLiveData;
    }

    public MediatorLiveData<List<MovieReview>> getMovieReviewsLiveData() {
        return movieReviewsLiveData;
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
