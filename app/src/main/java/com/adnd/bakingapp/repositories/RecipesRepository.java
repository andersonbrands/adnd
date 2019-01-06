package com.adnd.bakingapp.repositories;

import com.adnd.bakingapp.api.RecipesApi;
import com.adnd.bakingapp.api.RecipesListConverterFactory;
import com.adnd.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipesRepository {

    private static final String TAG = RecipesRepository.class.getSimpleName();

    public void loadRecipesFromWebService() {
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
                    // TODO handle result
                } else {
                    onRecipesCallError();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                onRecipesCallError();
            }

            private void onRecipesCallError() {
                // TODO handle call error
            }
        });
    }
}
