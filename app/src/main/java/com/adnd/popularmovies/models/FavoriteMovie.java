package com.adnd.popularmovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
    tableName = "favorite_movies",
    foreignKeys = @ForeignKey(
        entity = Movie.class,
        parentColumns = "id",
        childColumns = "movie_id"
    )
)
public class FavoriteMovie {

    @PrimaryKey
    private int movie_id;

    public FavoriteMovie(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
