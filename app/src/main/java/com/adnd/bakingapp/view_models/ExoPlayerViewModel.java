package com.adnd.bakingapp.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.adnd.bakingapp.exoplayer.ExoPlayerManager;
import com.google.android.exoplayer2.Player;

public class ExoPlayerViewModel extends AndroidViewModel {

    private MutableLiveData<Player> playerLiveData = new MutableLiveData<>();

    private ExoPlayerManager exoPlayerManager;

    public ExoPlayerViewModel(@NonNull Application application) {
        super(application);
        exoPlayerManager = new ExoPlayerManager();
        exoPlayerManager.initialize(application);
        playerLiveData.setValue(exoPlayerManager.getPlayer());
    }

    public MutableLiveData<Player> getPlayerLiveData() {
        return playerLiveData;
    }

    public void setSourceAndPrepare(String videoURL) {
        exoPlayerManager.setSourceAndPrepare(videoURL);
    }

    public void stopPlayer() {
        exoPlayerManager.stopPlayer();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        exoPlayerManager.release(getApplication());
    }
}
