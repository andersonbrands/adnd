package com.adnd.builditbigger.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.adnd.builditbigger.utils.EndpointsAsyncTask;

public class MainActivityViewModel extends ViewModel implements EndpointsAsyncTask.OnCallFinished {

    public static final int STATUS_IDLE = 0;
    public static final int STATUS_LOADING = 1;

    private final ObservableField<Integer> status = new ObservableField<>();

    private MutableLiveData<String> jokeLiveData = new MutableLiveData<>();

    public MainActivityViewModel() {
        status.set(STATUS_IDLE);
    }

    public ObservableField<Integer> getStatus() {
        return status;
    }

    public LiveData<String> getJokeLiveData() {
        return jokeLiveData;
    }

    public void tellJoke() {
        status.set(STATUS_LOADING);
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onCallFinished(String result) {
        jokeLiveData.setValue(result);
        status.set(STATUS_IDLE);
    }
}
