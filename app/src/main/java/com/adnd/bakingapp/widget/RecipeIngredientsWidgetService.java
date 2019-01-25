package com.adnd.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.repositories.RecipesRepository;

import java.util.List;


public class RecipeIngredientsWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGETS = "com.adnd.bakingapp.widget.action.update_widgets";

    public RecipeIngredientsWidgetService() {
        super("RecipeIngredientsWidgetService");
    }

    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, RecipeIngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                handleActionUpdateWidgets();
            }
        }
    }

    private void handleActionUpdateWidgets() {
        RecipesRepository repository = new RecipesRepository(getApplication());

        List<Integer> appWidgetsIds = repository.loadAllWidgetIds();

        for (Integer appWidgetId : appWidgetsIds) {
            int recipeId = repository.loadRecipeIdForWidget(appWidgetId);

            Recipe recipe = repository.loadRecipeById(recipeId);
            if (recipe != null) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

                RecipeIngredientsWidget.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId, recipe);
            }
        }
    }

}
