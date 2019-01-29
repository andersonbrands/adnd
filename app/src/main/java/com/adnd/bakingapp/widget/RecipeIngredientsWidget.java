package com.adnd.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.RecipeDetailsActivity;
import com.adnd.bakingapp.adapters.BindingAdapters;
import com.adnd.bakingapp.models.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    public static final String INGREDIENTS_LIST_TEXT = "IngredientsListText";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_JSON_EXTRA_KEY, recipe.toJSONString());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);

        String ingredientsListText =
                BindingAdapters.getIngredientsListText(context.getResources(), recipe.getIngredients());
        Intent remoteViewsServiceIntent = new Intent(context, RecipeIngredientsRemoteViewsService.class);
        remoteViewsServiceIntent.putExtra(INGREDIENTS_LIST_TEXT, ingredientsListText);
        views.setRemoteAdapter(R.id.lv_ingredients_list, remoteViewsServiceIntent);

        views.setTextViewText(R.id.tv_recipe_name, recipe.getName());

        views.setOnClickPendingIntent(R.id.recipe_ingredients_widget_root, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        RecipeIngredientsWidgetService.startActionDeleteWidgets(context, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if (appWidgetIds.length != 0) {
            RecipeIngredientsWidgetService.startActionUpdateWidgets(context);
        }
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

