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

    private static final String INVALID_VIDEL_URL = "invalid";
    private String lastVideoUrl = INVALID_VIDEL_URL;

    public ExoPlayerViewModel(@NonNull Application application) {
        super(application);
        exoPlayerManager = new ExoPlayerManager();
        playerLiveData.setValue(exoPlayerManager.getPlayer());
    }

    public MutableLiveData<Player> getPlayerLiveData() {
        return playerLiveData;
    }

    private void initializePlayer() {
        exoPlayerManager.initialize(getApplication());
        playerLiveData.setValue(exoPlayerManager.getPlayer());
    }

    public void startPlayer(String videoURL) {
        if (lastVideoUrl.equals(videoURL)) {
            initializePlayer();
            exoPlayerManager.setSourceAndPrepare(videoURL);
            exoPlayerManager.restorePlayerState();
        } else {
            if (lastVideoUrl.equals(INVALID_VIDEL_URL)) {
                initializePlayer();
            }
            exoPlayerManager.setSourceAndPrepare(videoURL);
            exoPlayerManager.resetPlayerState();
            exoPlayerManager.restorePlayerState();
        }

        lastVideoUrl = videoURL;
    }

    public void releasePlayer() {
        exoPlayerManager.release(getApplication());
    }

}
