package com.adnd.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.adnd.bakingapp.models.Ingredient;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.models.WidgetHasRecipe;

@Database(entities = {Recipe.class, Ingredient.class, Step.class, WidgetHasRecipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance = null;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "baking-app-db";

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RecipesDao recipesDao();

    public abstract IngredientsDao ingredientsDao();

    public abstract StepsDao stepsDao();

    public abstract WidgetHasRecipeDao widgetHasRecipeDao();
}
