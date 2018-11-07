package com.adnd.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.adnd.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_JSON_STRING_EXTRA_KEY = "movie_json_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MOVIE_JSON_STRING_EXTRA_KEY)) {

            String jsonString =
                    intentThatStartedThisActivity.getStringExtra(MOVIE_JSON_STRING_EXTRA_KEY);
            Movie movie = Movie.fromJSONString(jsonString);

            if (movie != null){
                TextView textView = findViewById(R.id.tv_original_title);
                textView.setText(movie.getOriginal_title());

                textView = findViewById(R.id.tv_release_date);
                textView.setText(getString(R.string.release_date_detail, movie.getRelease_date()));

                textView = findViewById(R.id.tv_vote_average);
                textView.setText(getString(R.string.vote_average_detail, movie.getVote_average()));

                textView = findViewById(R.id.tv_original_title);
                textView.setText(movie.getOriginal_title());

                textView = findViewById(R.id.tv_overview);
                textView.setText(movie.getOverview());

                ImageView imageView = findViewById(R.id.iv_poster_image);
                Picasso.get()
                        .load(movie.getPosterUrl())
                        .fit()
                        .into(imageView);
            }
        } else {
            finish();
        }
    }
}
