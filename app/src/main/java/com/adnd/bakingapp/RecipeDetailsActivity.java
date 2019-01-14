package com.adnd.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.adnd.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;

public class RecipeDetailsActivity extends AppCompatActivity {

    public final static String RECIPE_JSON_EXTRA_KEY = "recipe_json_extra_key";

    ActivityRecipeDetailsBinding binding;
    RecipeDetailsActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(RECIPE_JSON_EXTRA_KEY)) {

            model = ViewModelProviders.of(this).get(RecipeDetailsActivityViewModel.class);

            String recipeJsonString =
                    intentThatStartedThisActivity.getStringExtra(RECIPE_JSON_EXTRA_KEY);

            Recipe recipe = Recipe.fromJSONString(recipeJsonString);

            if (recipe != null) {
                Toast.makeText(this, "Recipe loaded: " + recipe.getName(), Toast.LENGTH_SHORT).show();
                model.setRecipe(recipe);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }
}
