package com.adnd.bakingapp.exoplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerManager implements Player.EventListener {

    private SimpleExoPlayer simpleExoPlayer;
    private DataSource.Factory dataSourceFactory;
    private static final String TAG = ExoPlayerManager.class.getSimpleName();

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateBuilder;

    private String lastVideoUrl = "";


    public SimpleExoPlayer getPlayer() {
        return simpleExoPlayer;
    }

    public ExoPlayerManager() {

    }

    public void initialize(Context context) {
        initializeMediaSession(context);
        initializePlayer(context);
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

    public void setSourceAndPrepare(String videoURL) {
        if (videoURL.equals(lastVideoUrl)) {
            return;
        }
        Uri uri = Uri.parse(videoURL);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(false);

        lastVideoUrl = videoURL;
    }

    public void release() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
        mediaSession.setActive(false);
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

    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mediaSession, intent);
        }
    }

}
