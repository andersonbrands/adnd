package com.adnd.bakingapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.adnd.bakingapp.api.RecipesApi;
import com.adnd.bakingapp.api.RecipesListConverterFactory;
import com.adnd.bakingapp.database.AppDatabase;
import com.adnd.bakingapp.database.IngredientsDao;
import com.adnd.bakingapp.database.RecipesDao;
import com.adnd.bakingapp.database.StepsDao;
import com.adnd.bakingapp.models.Ingredient;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;

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

    public RecipesRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        recipesDao = db.recipesDao();
        ingredientsDao = db.ingredientsDao();
        stepsDao = db.stepsDao();
    }

    public void saveRecipeToDatabase(Recipe recipe) {
        recipesDao.insert(recipe);
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setId(recipe.getId());
            ingredientsDao.insert(ingredient);
        }
        for (Step step : recipe.getSteps()) {
            step.setId(recipe.getId());
            stepsDao.insert(step);
        }
    }

    public LiveData<List<Recipe>> loadRecipes() {
        MutableLiveData<List<Recipe>> recipesListLiveData = new MutableLiveData<>();

        loadRecipesFromWebService(recipesListLiveData);

        return recipesListLiveData;
    }

    private void loadRecipesFromDatabase() {
        List<Recipe> recipes = recipesDao.getRecipes();
        for (Recipe recipe: recipes) {
            recipe.setIngredients(ingredientsDao.getIngredientsForRecipe(recipe.getId()));
            recipe.setSteps(stepsDao.getStepsForRecipe(recipe.getId()));
        }
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
