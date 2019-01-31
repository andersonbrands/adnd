package com.adnd.bakingapp.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ComingFromConfigChangeViewModel extends ViewModel {

    private MutableLiveData<Boolean> comingFromConfigChange = new MutableLiveData<>();

    public LiveData<Boolean> getComingFromConfigChange() {
        return comingFromConfigChange;
    }

    public void setComingFromConfigChange(boolean comingFromConfigChange) {
        this.comingFromConfigChange.setValue(comingFromConfigChange);
    }
}
