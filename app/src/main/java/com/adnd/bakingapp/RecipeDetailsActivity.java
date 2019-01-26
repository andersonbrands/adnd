package com.adnd.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;

public class RecipeDetailsActivity extends AppCompatActivity implements ListItemClickListener<Step> {

    public final static String RECIPE_JSON_EXTRA_KEY = "recipe_json_extra_key";

    ActivityRecipeDetailsBinding binding;
    RecipeDetailsActivityViewModel model;

    private boolean twoPane = false;

    @VisibleForTesting
    public boolean isTwoPane() {
        return twoPane;
    }

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
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(recipe.getName());
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                model.setRecipe(recipe);
                binding.setRecipe(recipe);

                if (binding.getRoot().findViewById(R.id.two_pane_layout) != null) {
                    twoPane = true;
                    if (savedInstanceState == null) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
