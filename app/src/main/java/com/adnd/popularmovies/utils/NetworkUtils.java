package com.adnd.popularmovies.utils;

import android.net.Uri;

import com.adnd.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String TMDb_BASE_URL = "https://api.themoviedb.org/3";
    private final static String MOVIE_PATH = "movie";
    private final static String POPULAR_PATH = "popular";
    private final static String TOP_RATED_PATH = "top_rated";
    private final static String API_KEY_PARAM = "api_key";

    public static URL buildPopularMoviesUrl() {
        return buildMoviesUrl(POPULAR_PATH);
    }

    public static URL buildTopRatedMoviesUrl() {
        return buildMoviesUrl(TOP_RATED_PATH);
    }

    private static URL buildMoviesUrl(String path) {
        Uri builtUri = Uri.parse(TMDb_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(path)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.TMDb_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
