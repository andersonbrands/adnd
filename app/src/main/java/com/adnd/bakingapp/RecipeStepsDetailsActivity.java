package com.adnd.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;

public class RecipeStepsDetailsActivity extends AppCompatActivity {

    public final static String RECIPE_JSON_EXTRA_KEY = "recipe_json_extra_key";
    public final static String RECIPE_STEP_POSITION_EXTRA_KEY = "recipe_step_position_extra_key";

    private RecipeDetailsActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(RECIPE_JSON_EXTRA_KEY)) {
            model = ViewModelProviders.of(this).get(RecipeDetailsActivityViewModel.class);

            String recipeJsonString =
                    intentThatStartedThisActivity.getStringExtra(RECIPE_JSON_EXTRA_KEY);

            Recipe recipe = Recipe.fromJSONString(recipeJsonString);

            if (recipe != null) {
                model.setRecipe(recipe);
                int recipeStepPosition =
                        intentThatStartedThisActivity.getIntExtra(RECIPE_STEP_POSITION_EXTRA_KEY, 0);
                // do not set selected position on rotation
                if (savedInstanceState == null) {
                    model.setSelectedStepPosition(recipeStepPosition);
                }
            } else {
                finish();
            }
        } else {
            finish();
        }

    }
}
