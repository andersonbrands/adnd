package com.adnd.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.bakingapp.models.WidgetHasRecipe;

import java.util.List;

@Dao
public interface WidgetHasRecipeDao {

    // TODO return live data
    @Query("SELECT recipe_id FROM widget_has_recipe WHERE widget_has_recipe.widget_id = :widgetId")
    int getRecipeIdByWidgetId(int widgetId);

    @Query("SELECT widget_id FROM widget_has_recipe")
    List<Integer> getAllWidgetIds();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WidgetHasRecipe widgetHasRecipe);
}
