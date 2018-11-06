package com.adnd.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.adnd.popularmovies.adapters.PosterAdapter;
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

    private RecyclerView mRecyclerView;
    private PosterAdapter mPosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_posters);

        URL queryUrl = NetworkUtils.buildPopularMoviesUrl();
        new TMDbQueryTask().execute(queryUrl);
    }

    private void loadPopularMovies() {
        URL queryUrl = NetworkUtils.buildPopularMoviesUrl();
        new TMDbQueryTask().execute(queryUrl);
    }

    private void loadTopRatedMovies() {
        URL queryUrl = NetworkUtils.buildTopRatedMoviesUrl();
        new TMDbQueryTask().execute(queryUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_popular:
                loadPopularMovies();
                break;
            case R.id.action_order_top_rated:
                loadTopRatedMovies();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView(List<Movie> movies) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mPosterAdapter = new PosterAdapter(movies);
        mRecyclerView.setAdapter(mPosterAdapter);
    }

    // TODO: handle "This AsyncTask should be static or leaks might occur" warning?
    public class TMDbQueryTask extends AsyncTask<URL, Void, String> {

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
                List<Movie> movies = getListOfMovies(result);
                setRecyclerView(movies);
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
