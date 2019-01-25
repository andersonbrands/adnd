package com.adnd.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.WidgetHasRecipe;

@Dao
public interface WidgetHasRecipeDao {

    // TODO return live data
    @Query("SELECT * FROM widget_has_recipe INNER JOIN recipes ON recipes.id = :widgetId")
    Recipe getRecipeByWidgetId(int widgetId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WidgetHasRecipe widgetHasRecipe);
}
