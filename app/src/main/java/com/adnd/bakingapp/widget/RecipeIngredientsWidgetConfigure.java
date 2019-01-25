package com.adnd.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.RecipeDetailsActivity;
import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.models.Recipe;

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

    private void configureWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RecipeIngredientsWidget.updateAppWidget(this, appWidgetManager, appWidgetId);
    }

    @Override
    public void onListItemClick(Recipe clickedItem, int clickedPosition) {

        configureWidget();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
