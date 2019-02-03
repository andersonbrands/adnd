package com.adnd.jokedisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA_KEY = "joke_extra_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(JOKE_EXTRA_KEY)) {
            String joke = intentThatStartedThisActivity.getStringExtra(JOKE_EXTRA_KEY);
            displayJoke(joke);
        } else {
            finish();
        }

    }

    private void displayJoke(String joke) {
        TextView jokeTextView = findViewById(R.id.tv_joke);
        if (jokeTextView != null) {
            jokeTextView.setText(joke);
        }
    }
}
