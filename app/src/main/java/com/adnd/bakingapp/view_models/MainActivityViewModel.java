package com.adnd.bakingapp.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.repositories.RecipesRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private RecipesRepository recipesRepository;

    private MediatorLiveData<List<Recipe>> recipesListLiveData = new MediatorLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        recipesRepository = new RecipesRepository(application);

        loadRecipesList();
    }

    public LiveData<List<Recipe>> getRecipeListLiveData() {
        return recipesListLiveData;
    }

    public void loadRecipesList() {
        final LiveData<List<Recipe>> source = recipesRepository.loadRecipes();
        recipesListLiveData.addSource(source, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes == null) {
                    // TODO unable to load recipes
                } else if (recipes.size() == 0) {
                    // TODO empty list
                }
                recipesListLiveData.setValue(recipes);
                recipesListLiveData.removeSource(source);
            }
        });
    }


}
