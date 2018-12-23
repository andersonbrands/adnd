package com.adnd.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.adnd.popularmovies.adapters.ListItemClickListener;
import com.adnd.popularmovies.adapters.MovieVideoAdapter;
import com.adnd.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.models.MovieVideo;
import com.adnd.popularmovies.view_models.MovieDetailActivityViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements ListItemClickListener<MovieVideo> {

    public static final String MOVIE_JSON_STRING_EXTRA_KEY = "movie_json_string";

    MovieDetailActivityViewModel model;

    private ActivityMovieDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MOVIE_JSON_STRING_EXTRA_KEY)) {

            model = ViewModelProviders.of(this).get(MovieDetailActivityViewModel.class);

            model.getMovieIsFavorite().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    binding.setMovieIsFavorite(aBoolean);
                }
            });

            model.getMovieVideosLiveData().observe(this, new Observer<List<MovieVideo>>() {
                @Override
                public void onChanged(@Nullable List<MovieVideo> movieVideos) {
                    setMovieVideosRecyclerView(movieVideos);
                }
            });

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            String jsonString =
                    intentThatStartedThisActivity.getStringExtra(MOVIE_JSON_STRING_EXTRA_KEY);
            Movie movie = Movie.fromJSONString(jsonString);

            if (movie != null) {
                model.init(movie);
                binding.setMovie(movie);
                binding.setViewModel(model);

                Picasso.get()
                        .load(movie.getPosterUrl())
                        .placeholder(R.drawable.ic_image_white_24dp)
                        .error(R.drawable.ic_error_outline_white_24dp)
                        .fit()
                        .into(binding.ivPosterImage);
            }
        } else {
            finish();
        }
    }

    private void setMovieVideosRecyclerView(List<MovieVideo> movieVideos) {
        final int gridCols = getResources().getInteger(R.integer.movie_video_cols);
        binding.rvMovieVideos.setLayoutManager(new GridLayoutManager(this, gridCols));
        MovieVideoAdapter adapter = new MovieVideoAdapter(movieVideos, this);
        binding.rvMovieVideos.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(MovieVideo clickedItem) {
        Toast.makeText(this, "clicked item: " + clickedItem.getName(), Toast.LENGTH_LONG).show();
    }
}
