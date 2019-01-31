package com.adnd.bakingapp.exoplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.adnd.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerManager extends BroadcastReceiver implements Player.EventListener {

    private SimpleExoPlayer simpleExoPlayer;
    private DataSource.Factory dataSourceFactory;
    private static final String TAG = ExoPlayerManager.class.getSimpleName();

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateBuilder;

    private int currentWindow = 0;
    private long playbackPosition = 0;

    public SimpleExoPlayer getPlayer() {
        return simpleExoPlayer;
    }

    public ExoPlayerManager() {

    }

    public void resetPlayerState() {
        currentWindow = simpleExoPlayer.getCurrentWindowIndex();
        playbackPosition = 0;
    }

    public void initialize(Context context) {
        initializeMediaSession(context);
        initializePlayer(context);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);

        context.registerReceiver(this, intentFilter);
    }

    private void initializeMediaSession(Context context) {
        mediaSession = new MediaSessionCompat(context, TAG);

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);

        playbackStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(playbackStateBuilder.build());

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                simpleExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                simpleExoPlayer.setPlayWhenReady(false);
            }
        });

        mediaSession.setActive(true);
    }

    private void initializePlayer(Context context) {
        if (simpleExoPlayer == null) {
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);

            simpleExoPlayer.addListener(this);

            dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, context.getResources().getString(R.string.app_name)));
        }
    }

    public void restorePlayerState() {
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);
    }

    private void savePlayerState() {
        playbackPosition = simpleExoPlayer.getCurrentPosition();
        currentWindow = simpleExoPlayer.getCurrentWindowIndex();
    }

    public void setSourceAndPrepare(String videoURL) {
        Uri uri = Uri.parse(videoURL);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        simpleExoPlayer.prepare(videoSource);
    }

    public void release(Context context) {
        if (simpleExoPlayer != null) {
            savePlayerState();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            mediaSession.setCallback(null);
            mediaSession.setActive(false);

            context.unregisterReceiver(this);
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == Player.STATE_READY) && playWhenReady) {
            playbackStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == Player.STATE_READY)) {
            playbackStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(playbackStateBuilder.build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
    }

}
