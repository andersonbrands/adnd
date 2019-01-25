package com.adnd.bakingapp.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.repositories.RecipesRepository;

import java.util.List;

public class RecipesListViewModel extends AndroidViewModel {

    public enum Status {
        Idle,
        Loading,
        Error,
        SuccessEmpty,
        Success
    }

    private final ObservableField<Status> status = new ObservableField<>();

    public ObservableField<Status> getStatus() {
        return status;
    }

    private void setStatus(Status status) {
        switch (status) {
            case Loading:
                statusTextResId.set(R.string.loading_data);
                break;
            case Error:
                statusTextResId.set(R.string.err_something_wrong);
                break;
            case SuccessEmpty:
                statusTextResId.set(R.string.empty_list);
                break;
            case Idle:
                statusTextResId.set(R.string.ellipsis);
            case Success:
                statusTextResId.set(R.string.ellipsis);
                break;
        }
        this.status.set(status);
    }

    private final ObservableInt statusTextResId = new ObservableInt();
    private RecipesRepository recipesRepository;
    private MediatorLiveData<List<Recipe>> recipesListLiveData = new MediatorLiveData<>();
    private MutableLiveData<Boolean> runningOnBackground = new MutableLiveData<>();

    public RecipesListViewModel(@NonNull Application application) {
        super(application);

        setStatus(Status.Idle);

        recipesRepository = new RecipesRepository(application);

        loadRecipesList();
    }

    public LiveData<List<Recipe>> getRecipeListLiveData() {
        return recipesListLiveData;
    }

    public LiveData<Boolean> getRunningOnBackground() {
        return runningOnBackground;
    }

    public ObservableInt getStatusTextResId() {
        return statusTextResId;
    }

    public void loadRecipesList() {
        runningOnBackground.setValue(true);
        setStatus(Status.Loading);
        final LiveData<List<Recipe>> source = recipesRepository.loadRecipes();
        recipesListLiveData.addSource(source, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes == null) {
                    setStatus(Status.Error);
                } else if (recipes.size() == 0) {
                    setStatus(Status.SuccessEmpty);
                } else {
                    setStatus(Status.Success);
                }
                recipesListLiveData.setValue(recipes);
                recipesListLiveData.removeSource(source);
                runningOnBackground.setValue(false);
            }
        });
    }

}
