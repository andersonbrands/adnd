package com.adnd.popularmovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "movies")
public class Movie {

    private String poster_path;
    private String original_title;
    private String overview;
    private float vote_average;
    private String release_date;

    @PrimaryKey
    private int id;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPosterUrl() {
        final String BASE_URL = "https://image.tmdb.org/t/p/";
        // size is one of "w92", "w154", "w185", "w342", "w500", "w780", or "original"
        final String SIZE_PATH = "w185"; // "w185" is recommended for most phones

        return BASE_URL + SIZE_PATH + getPoster_path();
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Movie fromJSONString(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return fromJSONObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJSONString() {
        return toJSONObject().toString();
    }

    public static Movie fromJSONObject(JSONObject jsonObject) {
        Movie movie = new Movie();

        try {
            movie.poster_path = jsonObject.getString("poster_path");
            movie.original_title = jsonObject.getString("original_title");
            movie.overview = jsonObject.getString("overview");
            movie.vote_average = Float.parseFloat(jsonObject.getString("vote_average"));
            movie.release_date = jsonObject.getString("release_date");
            movie.id = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            movie = null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            movie = null;
        }

        return movie;
    }
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("poster_path", this.poster_path);
            jsonObject.put("original_title", this.original_title);
            jsonObject.put("overview", this.overview);
            jsonObject.put("vote_average", this.vote_average);
            jsonObject.put("release_date", this.release_date);
            jsonObject.put("id", this.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
