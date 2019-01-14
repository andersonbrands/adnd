package com.adnd.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.adnd.bakingapp.IdlingResource.SimpleIdlingResource;
import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.adapters.RecipeAdapter;
import com.adnd.bakingapp.databinding.ActivityMainBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener<Recipe> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private MainActivityViewModel model;

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getIdlingResource();

        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        binding.setModel(model);

        model.getRecipeListLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                setRecyclerView(recipes);
            }
        });

        model.getRunningOnBackground().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean runningOnBackground) {
                if (idlingResource != null && runningOnBackground != null) {
                    idlingResource.setIdleState(!runningOnBackground);
                }
            }
        });
    }

    private void setRecyclerView(List<Recipe> recipes) {
        binding.rvRecipes.setLayoutManager(new GridLayoutManager(this, 1));
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes, this);
        binding.rvRecipes.setAdapter(recipeAdapter);
        binding.setAdapter(recipeAdapter);
    }

    @Override
    public void onListItemClick(Recipe clickedItem, int clickedPosition) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_JSON_EXTRA_KEY, clickedItem.toJSONString());
        startActivity(intent);
    }
}
