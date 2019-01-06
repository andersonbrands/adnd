package com.adnd.bakingapp.api;

import com.adnd.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApi {

    String BASE_URL = "https://go.udacity.com/";

    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();

}
