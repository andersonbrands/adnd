package com.adnd.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.adnd.popularmovies.adapters.ListItemClickListener;
import com.adnd.popularmovies.adapters.PosterAdapter;
import com.adnd.popularmovies.databinding.ActivityMainBinding;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.view_models.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener<Movie> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PosterAdapter mPosterAdapter;

    private ActivityMainBinding binding;

    private MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        binding.setModel(model);

        model.getMoviesListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                setRecyclerView(movies);
            }
        });
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
                model.loadPopularMovies();
                break;
            case R.id.action_order_top_rated:
                model.loadTopRatedMovies();
                break;
            case R.id.action_favorites:
                model.loadFavoriteMovies();
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
        binding.rvPosters.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.poster_grid_cols)));
        mPosterAdapter = new PosterAdapter(movies, this);
        binding.rvPosters.setAdapter(mPosterAdapter);
        binding.setAdapter(mPosterAdapter);
    }

}
