package com.adnd.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.popularmovies.models.Movie;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("DELETE FROM movies WHERE movies.id = :id")
    void deleteMovieById(int id);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    LiveData<List<Movie>> loadMoviesByIds(List<Integer> ids);

}
