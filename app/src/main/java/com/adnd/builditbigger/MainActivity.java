package com.adnd.builditbigger;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
                displayJoke(value);
            }
        });

    }

    private void displayJoke(String joke) {
        if (joke != null) {
            Intent intent = new Intent(this, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_EXTRA_KEY, joke);
            startActivity(intent);
        } else {
            String error_msg = getString(R.string.unable_to_get_joke);
            Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
        }
    }
}
