package com.adnd.bakingapp.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.adnd.bakingapp.exoplayer.ExoPlayerManager;
import com.adnd.bakingapp.models.Recipe;
import com.google.android.exoplayer2.Player;

public class RecipeDetailsActivityViewModel extends ViewModel {

    private MutableLiveData<Integer> selectedStepPosition = new MutableLiveData<>();

    private MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

    public MutableLiveData<Recipe> getRecipeLiveData() {
        return recipeLiveData;
    }

    public void setRecipe(Recipe recipe) {
        this.recipeLiveData.setValue(recipe);
    }

    public MutableLiveData<Integer> getSelectedStepPosition() {
        return selectedStepPosition;
    }

    public void setSelectedStepPosition(int selectedStepPosition) {
        this.selectedStepPosition.setValue(selectedStepPosition);
    }

}
