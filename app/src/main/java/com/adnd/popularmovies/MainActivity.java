package com.adnd.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adnd.popularmovies.adapters.PosterAdapter;
import com.adnd.popularmovies.api.MovieListConverterFactory;
import com.adnd.popularmovies.api.TMDbApi;
import com.adnd.popularmovies.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements PosterAdapter.ListItemClickListener, Callback<List<Movie>> {

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
        loadMovies(POPULAR_MOVIES_KEY);
    }

    private void loadTopRatedMovies() {
        loadMovies(TOP_RATED_MOVIES_KEY);
    }

    private void loadMovies(String key) {
        Retrofit fit = new Retrofit.Builder()
                .baseUrl(TMDbApi.BASE_URL)
                .addConverterFactory(new MovieListConverterFactory())
                .build();

        TMDbApi api = fit.create(TMDbApi.class);

        Call<List<Movie>> moviesCall = (key.equals(POPULAR_MOVIES_KEY)) ? api.getPopularMovies() : api.getTopRatedMovies();

        moviesCall.enqueue(this);
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

    @Override
    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        if (response.isSuccessful() && response.body() != null) {
            setRecyclerView(response.body());
        } else {
            onMovieListCallError();
        }
    }

    @Override
    public void onFailure(Call<List<Movie>> call, Throwable t) {
        onMovieListCallError();
        Log.d(TAG, t.getMessage());
    }

    private void onMovieListCallError() {
        Toast.makeText(this, getString(R.string.err_something_wrong_try_again), Toast.LENGTH_LONG).show();
    }


}
