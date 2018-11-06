package com.adnd.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    private String poster_path;
    private String original_title;
    private String overview;
    private float vote_average;
    private String release_date;

    public String getPoster_path() {
        return poster_path;
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

    public String getOverview() {
        return overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
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
    public static Movie fromJSONObject(JSONObject jsonObject) {
        Movie movie = new Movie();

        try {
            movie.poster_path = jsonObject.getString("poster_path");
            movie.original_title = jsonObject.getString("original_title");
            movie.overview = jsonObject.getString("overview");
            movie.vote_average = Float.parseFloat(jsonObject.getString("vote_average"));
            movie.release_date = jsonObject.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
            movie = null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            movie = null;
        }

        return movie;
    }
}
