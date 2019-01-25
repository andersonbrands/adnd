package com.adnd.bakingapp.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.repositories.RecipesRepository;

import java.util.List;

public class RecipesListViewModel extends AndroidViewModel {

    private final ObservableInt emptyListTextResId = new ObservableInt();
    private RecipesRepository recipesRepository;
    private MediatorLiveData<List<Recipe>> recipesListLiveData = new MediatorLiveData<>();
    private MutableLiveData<Boolean> runningOnBackground = new MutableLiveData<>();

    public RecipesListViewModel(@NonNull Application application) {
        super(application);
        recipesRepository = new RecipesRepository(application);

        emptyListTextResId.set(R.string.empty_list);

        loadRecipesList();
    }

    public LiveData<List<Recipe>> getRecipeListLiveData() {
        return recipesListLiveData;
    }

    public LiveData<Boolean> getRunningOnBackground() {
        return runningOnBackground;
    }

    public ObservableInt getEmptyListTextResId() {
        return emptyListTextResId;
    }

    public void loadRecipesList() {
        runningOnBackground.setValue(true);
        final LiveData<List<Recipe>> source = recipesRepository.loadRecipes();
        recipesListLiveData.addSource(source, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes == null) {
                    emptyListTextResId.set(R.string.err_something_wrong);
                } else if (recipes.size() == 0) {
                    emptyListTextResId.set(R.string.empty_list);
                }
                recipesListLiveData.setValue(recipes);
                recipesListLiveData.removeSource(source);
                runningOnBackground.setValue(false);
            }
        });
    }


}
