package com.adnd.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.adnd.popularmovies.adapters.PosterAdapter;
import com.adnd.popularmovies.api.MovieListConverterFactory;
import com.adnd.popularmovies.api.TMDbApi;
import com.adnd.popularmovies.models.Movie;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements PosterAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String POPULAR_MOVIES_KEY = "popular";
    private static final String TOP_RATED_MOVIES_KEY = "top_rated";

    private RecyclerView mRecyclerView;
    private PosterAdapter mPosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_posters);

        loadPopularMovies();
    }

    private void loadPopularMovies() {
        new TMDbQueryTask().execute(POPULAR_MOVIES_KEY);
    }

    private void loadTopRatedMovies() {
        new TMDbQueryTask().execute(TOP_RATED_MOVIES_KEY);
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

    @Override
    public void onListItemClick(Movie clickedItem) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_JSON_STRING_EXTRA_KEY, clickedItem.toJSONString());
        startActivity(intent);
    }

    private void setRecyclerView(List<Movie> movies) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mPosterAdapter = new PosterAdapter(movies, this);
        mRecyclerView.setAdapter(mPosterAdapter);
    }

    // TODO: handle "This AsyncTask should be static or leaks might occur" warning?
    public class TMDbQueryTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            String key = params[0];
            List<Movie> result = null;

            Retrofit fit = new Retrofit.Builder()
                    .baseUrl(TMDbApi.BASE_URL)
                    .addConverterFactory(new MovieListConverterFactory())
                    .build();

            TMDbApi api = fit.create(TMDbApi.class);

            Call<List<Movie>> moviesCall = (key.equals(POPULAR_MOVIES_KEY)) ? api.getPopularMovies() : api.getTopRatedMovies();

            try {
                Response<List<Movie>> rrr = moviesCall.execute();
                result = rrr.body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            if (result != null) {
                setRecyclerView(result);
            }
        }

    }

}
