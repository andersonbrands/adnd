package com.adnd.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.bakingapp.models.WidgetHasRecipe;

import java.util.List;

@Dao
public interface WidgetHasRecipeDao {

    @Query("SELECT * FROM widget_has_recipe")
    List<WidgetHasRecipe> getAllWidgetHasRecipes();

    @Query("SELECT * FROM widget_has_recipe WHERE widget_id = :widgetId")
    LiveData<WidgetHasRecipe> getWidgetHasRecipeById(int widgetId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WidgetHasRecipe widgetHasRecipe);
}
