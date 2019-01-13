package com.adnd.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.adnd.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    public final static String RECIPE_JSON_EXTRA_KEY = "recipe_json_extra_key";

    ActivityRecipeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(RECIPE_JSON_EXTRA_KEY)) {

            String recipeJsonString =
                    intentThatStartedThisActivity.getStringExtra(RECIPE_JSON_EXTRA_KEY);

            Recipe recipe = Recipe.fromJSONString(recipeJsonString);

            if (recipe != null) {
                Toast.makeText(this, "Recipe loaded: " + recipe.getName(), Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }
}
