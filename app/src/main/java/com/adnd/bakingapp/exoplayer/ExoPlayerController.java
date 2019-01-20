package com.adnd.bakingapp.exoplayer;

import android.content.Context;
import android.net.Uri;

import com.adnd.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerController {

    private SimpleExoPlayer simpleExoPlayer;
    private DataSource.Factory dataSourceFactory;

    public void create(Context context) {
        if (simpleExoPlayer != null) {
            release();
        }
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
        dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getResources().getString(R.string.app_name)));
    }

    public void release() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            dataSourceFactory = null;
        }
    }

    public boolean isPlaying() {
        return (simpleExoPlayer.getPlaybackState() == Player.STATE_IDLE && simpleExoPlayer.getPlayWhenReady());
    }

    public boolean isPaused() {
        return (simpleExoPlayer.getPlaybackState() == Player.STATE_IDLE && !simpleExoPlayer.getPlayWhenReady());
    }

    public void pause() {
        simpleExoPlayer.setPlayWhenReady(false);
    }

    public void resume() {
        simpleExoPlayer.setPlayWhenReady(true);
    }

    public void setSourceAndPrepare(String videoURL) {
        Uri uri = Uri.parse(videoURL);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        simpleExoPlayer.prepare(videoSource);
    }

    public void setToPlayerView(PlayerView playerView) {
        playerView.setPlayer(simpleExoPlayer);
    }

}
