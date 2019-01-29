package com.adnd.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adnd.bakingapp.R;

import static com.adnd.bakingapp.widget.RecipeIngredientsWidget.INGREDIENTS_LIST_TEXT;

public class RecipeIngredientsRemoteViewsService extends RemoteViewsService {
    public RecipeIngredientsRemoteViewsService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeIngredientsWidgetRemoteViewsFactory(getApplicationContext(), intent);
    }

    class RecipeIngredientsWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private String ingredientsListText;

        RecipeIngredientsWidgetRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            ingredientsListText = intent.getStringExtra(INGREDIENTS_LIST_TEXT);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget_list_item);
            views.setTextViewText(R.id.tv_recipe_ingredients_details, ingredientsListText);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
