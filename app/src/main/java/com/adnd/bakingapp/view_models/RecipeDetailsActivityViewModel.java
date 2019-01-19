package com.adnd.bakingapp.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.adnd.bakingapp.exoplayer.ExoPlayerManager;
import com.adnd.bakingapp.models.Recipe;
import com.google.android.exoplayer2.ui.PlayerView;

public class RecipeDetailsActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> selectedStepPosition = new MutableLiveData<>();

    private MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

    private ExoPlayerManager exoPlayerManager;

    public RecipeDetailsActivityViewModel(@NonNull Application application) {
        super(application);
        exoPlayerManager = new ExoPlayerManager();
        exoPlayerManager.initialize(application);
    }

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
        this.selectedStepPosition.setValue(selectedStepPosition);;
    }

    public void setSourceAndPrepare(String videoURL, PlayerView playerView) {
        exoPlayerManager.setSourceAndPrepare(videoURL, playerView);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        exoPlayerManager.release();
    }
}
