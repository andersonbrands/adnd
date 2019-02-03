package com.adnd.builditbigger;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adnd.builditbigger.view_models.MainActivityViewModel;
import com.adnd.jokedisplay.JokeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        model.getJokeLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
                if (value != null) {
                    displayJoke(value);
                }
            }
        });

    }

    private void displayJoke(String joke) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_EXTRA_KEY, joke);
        startActivity(intent);
    }
}
