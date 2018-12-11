package com.adnd.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adnd.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.adnd.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_JSON_STRING_EXTRA_KEY = "movie_json_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MOVIE_JSON_STRING_EXTRA_KEY)) {

            String jsonString =
                    intentThatStartedThisActivity.getStringExtra(MOVIE_JSON_STRING_EXTRA_KEY);
            Movie movie = Movie.fromJSONString(jsonString);

            if (movie != null) {
                binding.setMovie(movie);

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
}
