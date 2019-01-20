package com.adnd.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;

public class RecipeDetailsActivity extends AppCompatActivity  implements ListItemClickListener<Step> {

    public final static String RECIPE_JSON_EXTRA_KEY = "recipe_json_extra_key";

    ActivityRecipeDetailsBinding binding;
    RecipeDetailsActivityViewModel model;

    private boolean twoPane = false;

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
                model.setRecipe(recipe);
                binding.setRecipe(recipe);

                if (binding.getRoot().findViewById(R.id.two_pane_layout) != null) {
                    twoPane = true;
                    if(savedInstanceState == null) {
                        model.setSelectedStepPosition(0);
                    }
                }
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onListItemClick(Step clickedItem, int position) {
        if (twoPane) {
            model.setSelectedStepPosition(position);
        } else {
            Intent intent = new Intent(this, RecipeStepsDetailsActivity.class);
            intent.putExtra(RecipeStepsDetailsActivity.RECIPE_JSON_EXTRA_KEY, binding.getRecipe().toJSONString());
            intent.putExtra(RecipeStepsDetailsActivity.RECIPE_STEP_POSITION_EXTRA_KEY, position);
            startActivity(intent);
        }
    }
}
