package com.adnd.popularmovies.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.adnd.popularmovies.database.AppDatabase;
import com.adnd.popularmovies.database.FavoriteMoviesDao;
import com.adnd.popularmovies.database.MoviesDao;
import com.adnd.popularmovies.models.FavoriteMovie;
import com.adnd.popularmovies.models.Movie;

import java.util.List;

public class MoviesRepository {

    private MoviesDao moviesDao;
    private FavoriteMoviesDao favoriteMoviesDao;

    public MoviesRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        favoriteMoviesDao = db.favoriteMoviesDao();
        moviesDao = db.MoviesDao();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMoviesDao.getFavoriteMovies();
    }

    public void addToFavorites(Movie movie) {
        // when inserting favorite movie we also need to inserting the movie itself
        moviesDao.insert(movie);
        favoriteMoviesDao.insert(new FavoriteMovie(movie.getId()));
    }

    public void deleteFavoriteMovie(FavoriteMovie favoriteMovie) {
        favoriteMoviesDao.deleteFavoriteMovie(favoriteMovie);

        // for now when deleting a favorite movie we also delete the movie itself
        deleteMovie(favoriteMovie.getMovie_id());
    }

    public void deleteMovie(Movie movie) {
        moviesDao.deleteMovie(movie);
    }

    public void deleteMovie(int movieId) {
        moviesDao.deleteMovieById(movieId);
    }

}
