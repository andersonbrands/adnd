package com.adnd.bakingapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.adnd.bakingapp.AppExecutors;
import com.adnd.bakingapp.api.RecipesApi;
import com.adnd.bakingapp.api.RecipesListConverterFactory;
import com.adnd.bakingapp.database.AppDatabase;
import com.adnd.bakingapp.database.IngredientsDao;
import com.adnd.bakingapp.database.RecipesDao;
import com.adnd.bakingapp.database.StepsDao;
import com.adnd.bakingapp.database.WidgetHasRecipeDao;
import com.adnd.bakingapp.models.Ingredient;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.models.WidgetHasRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipesRepository {

    private static final String TAG = RecipesRepository.class.getSimpleName();

    private RecipesDao recipesDao;
    private IngredientsDao ingredientsDao;
    private StepsDao stepsDao;
    private WidgetHasRecipeDao widgetHasRecipeDao;

    public RecipesRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        recipesDao = db.recipesDao();
        ingredientsDao = db.ingredientsDao();
        stepsDao = db.stepsDao();
        widgetHasRecipeDao = db.widgetHasRecipeDao();
    }

    public LiveData<List<Recipe>> loadRecipes() {
        MutableLiveData<List<Recipe>> recipesListLiveData = new MutableLiveData<>();

        loadRecipesFromWebService(recipesListLiveData);

        return recipesListLiveData;
    }

    public void saveWidgetHasRecipeToDatabase(final Recipe recipe, int widgetId) {
        final WidgetHasRecipe widgetHasRecipe = new WidgetHasRecipe();
        widgetHasRecipe.setWidget_id(widgetId);
        widgetHasRecipe.setRecipe_id(recipe.getId());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                recipesDao.insert(recipe);
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredient.setRecipe_id(recipe.getId());
                    ingredientsDao.insert(ingredient);
                }
                for (Step step : recipe.getSteps()) {
                    step.setRecipe_id(recipe.getId());
                    stepsDao.insert(step);
                }

                widgetHasRecipeDao.insert(widgetHasRecipe);
            }
        });
    }

    public LiveData<WidgetHasRecipe> loadWidgetHasRecipeById(int widgetId) {
        return widgetHasRecipeDao.getWidgetHasRecipeById(widgetId);
    }

    public List<WidgetHasRecipe> loadAllWidgetHasRecipe() {
        return widgetHasRecipeDao.getAllWidgetHasRecipes();
    }

    public Recipe loadRecipeById(int recipeId) {
        Recipe recipe = recipesDao.getRecipeById(recipeId);
        recipe.setIngredients(ingredientsDao.getIngredientsForRecipe(recipeId));
        recipe.setSteps(stepsDao.getStepsForRecipe(recipeId));
        return recipe;
    }

    private void loadRecipesFromWebService(final MutableLiveData<List<Recipe>> recipesListLiveData) {
        Retrofit fit = new Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .addConverterFactory(new RecipesListConverterFactory())
                .build();

        RecipesApi api = fit.create(RecipesApi.class);

        Call<List<Recipe>> recipesCall = api.getRecipes();

        recipesCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recipesListLiveData.setValue(response.body());
                } else {
                    onRecipesCallError();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                onRecipesCallError();
            }

            private void onRecipesCallError() {
                recipesListLiveData.setValue(null);
            }
        });
    }
}
