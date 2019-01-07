package com.adnd.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.bakingapp.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientsDao {

    // TODO return livedata
    @Query("SELECT * FROM ingredients WHERE recipe_id = :recipe_id")
    List<Ingredient> getIngredientsForRecipe(int recipe_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Ingredient> ingredients);

    @Query("SELECT COUNT(recipe_id) FROM ingredients WHERE recipe_id = :recipe_id")
    int getIngredientsForRecipeCount(int recipe_id);

}
