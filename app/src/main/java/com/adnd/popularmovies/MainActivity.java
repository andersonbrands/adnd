package com.adnd.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class TMDbQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL queryUrl = params[0];
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(queryUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.equals("")) {
                Log.d(TAG, "Result from query:\n" + result);
                List<Movie> movies = getListOfMovies(result);
                // TODO: use acquired list of movies
            }
        }

        private List<Movie> getListOfMovies(String result) {
            List<Movie> movies = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Movie movie = Movie.fromJSONObject(jsonArray.getJSONObject(i));
                    if (movie != null) {
                        movies.add(movie);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }
    }

}
