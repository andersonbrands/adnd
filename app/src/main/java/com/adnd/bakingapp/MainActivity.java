package com.adnd.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        model.getRecipeListLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                setRecyclerView(recipes);
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
    public void onListItemClick(Recipe clickedItem) {
        Log.d(TAG, "Clicked recipe: " + clickedItem.getName());
    }
}
