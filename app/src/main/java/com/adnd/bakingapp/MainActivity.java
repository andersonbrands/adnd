package com.adnd.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.adnd.bakingapp.IdlingResource.SimpleIdlingResource;
import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.RecipesListViewModel;

public class MainActivity extends AppCompatActivity implements ListItemClickListener<Recipe> {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Nullable
    private SimpleIdlingResource idlingResource;

    @VisibleForTesting
    @Nullable
    public SimpleIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getIdlingResource();

        RecipesListViewModel model = ViewModelProviders.of(this).get(RecipesListViewModel.class);

        model.getRunningOnBackground().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean runningOnBackground) {
                if (idlingResource != null && runningOnBackground != null) {
                    idlingResource.setIdleState(!runningOnBackground);
                }
            }
        });
    }

    @Override
    public void onListItemClick(Recipe clickedItem, int clickedPosition) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_JSON_EXTRA_KEY, clickedItem.toJSONString());
        startActivity(intent);
    }
}
