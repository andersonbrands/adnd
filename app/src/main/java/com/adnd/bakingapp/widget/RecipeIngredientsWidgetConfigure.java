package com.adnd.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.WidgetHasRecipe;
import com.adnd.bakingapp.repositories.RecipesRepository;

public class RecipeIngredientsWidgetConfigure extends AppCompatActivity implements ListItemClickListener<Recipe> {

    private int appWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients_widget_configure);

        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

    }

    @Override
    public void onListItemClick(Recipe clickedItem, int clickedPosition) {
        RecipesRepository repository = new RecipesRepository(getApplication());

        repository.saveWidgetHasRecipeToDatabase(clickedItem, appWidgetId);

        final LiveData<WidgetHasRecipe> liveData = repository.loadWidgetHasRecipeById(appWidgetId);

        liveData.observe(this, new Observer<WidgetHasRecipe>() {
            @Override
            public void onChanged(@Nullable WidgetHasRecipe widgetHasRecipe) {
                if (widgetHasRecipe != null) {
                    liveData.removeObserver(this);
                    finishConfiguration();
                }
            }
        });
    }

    private void finishConfiguration() {
        RecipeIngredientsWidgetService.startActionUpdateWidgets(this);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);

        finish();
    }
}
