package com.adnd.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.bakingapp.models.Step;

import java.util.List;

@Dao
public interface StepsDao {

    @Query("SELECT * FROM steps WHERE recipe_id = :recipe_id")
    List<Step> getStepsForRecipe(int recipe_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Step step);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Step> steps);

}
