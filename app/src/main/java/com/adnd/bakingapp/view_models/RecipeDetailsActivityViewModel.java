package com.adnd.bakingapp.view_models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.adnd.bakingapp.models.Recipe;

public class RecipeDetailsActivityViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

    public RecipeDetailsActivityViewModel() {

    }

    public MutableLiveData<Recipe> getRecipeLiveData() {
        return recipeLiveData;
    }

    public void setRecipe(Recipe recipe) {
        this.recipeLiveData.setValue(recipe);
    }
}
