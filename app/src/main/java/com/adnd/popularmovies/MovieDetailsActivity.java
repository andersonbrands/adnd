package com.adnd.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.adnd.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.adnd.popularmovies.models.Movie;
import com.adnd.popularmovies.view_models.MovieDetailActivityViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_JSON_STRING_EXTRA_KEY = "movie_json_string";

    MovieDetailActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MOVIE_JSON_STRING_EXTRA_KEY)) {

            model = ViewModelProviders.of(this).get(MovieDetailActivityViewModel.class);

            model.getMovieIsFavorite().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    binding.setMovieIsFavorite(aBoolean);
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
}
