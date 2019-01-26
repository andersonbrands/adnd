package com.adnd.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

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
                ActionBar actionBar = getSupportActionBar();

                if (actionBar != null) {
                    actionBar.setTitle(recipe.getName());
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }

                // go fullscreen when on landscape mode
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setTheme(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    if (actionBar != null) {
                        actionBar.hide();
                    }
                }
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
