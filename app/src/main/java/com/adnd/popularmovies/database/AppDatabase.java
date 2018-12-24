package com.adnd.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.adnd.popularmovies.models.FavoriteMovie;
import com.adnd.popularmovies.models.Movie;


@Database(entities = {Movie.class, FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance = null;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popular-movies-db";

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavoriteMoviesDao favoriteMoviesDao();
    public abstract MoviesDao MoviesDao();

}