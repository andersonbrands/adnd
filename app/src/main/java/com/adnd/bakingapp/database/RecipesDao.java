package com.adnd.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.bakingapp.models.Recipe;

import java.util.List;

@Dao
public interface RecipesDao {

    // TODO return livedata
    @Query("SELECT * FROM recipes")
    List<Recipe> getRecipes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Recipe> recipes);

}
