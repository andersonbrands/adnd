package com.adnd.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.adnd.popularmovies.models.FavoriteMovie;
import com.adnd.popularmovies.models.Movie;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM favorite_movies INNER JOIN movies ON movies.id = favorite_movies.movie_id")
    LiveData<List<Movie>> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteMovie favoriteMovie);

    @Delete
    void deleteFavoriteMovie(FavoriteMovie favoriteMovie);

    @Query("SELECT CAST(COUNT(*) AS BIT) FROM movies WHERE id = :movieId")
    boolean isFavorite(int movieId);
}
