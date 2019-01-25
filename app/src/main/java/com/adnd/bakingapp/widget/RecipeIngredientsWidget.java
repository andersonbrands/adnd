package com.adnd.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.adnd.bakingapp.MainActivity;
import com.adnd.bakingapp.R;
import com.adnd.bakingapp.adapters.BindingAdapters;
import com.adnd.bakingapp.models.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);

        views.setTextViewText(R.id.tv_recipe_name, recipe.getName());

        String ingredientsListText =
                BindingAdapters.getIngredientsListText(context.getResources(), recipe.getIngredients());

        views.setTextViewText(R.id.tv_recipe_ingredients_details, ingredientsListText);

        views.setOnClickPendingIntent(R.id.recipe_ingredients_widget_root, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeIngredientsWidgetService.startActionUpdateWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

