package io.github.firemaples.livestreamingpoc.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Locale;

import io.github.firemaples.livestreamingpoc.R;

public class HLSPlayerActivity extends Activity {
    private static final String INTENT_ROOM_NAME = "roomName";

    private static final String URI_FORMAT = "http://192.168.1.6:8080/hls/%s/index.m3u8";

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private Uri hlsUri;

    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    //    private TrackSelectionHelper trackSelectionHelper;
    private DebugTextViewHelper debugViewHelper;
    private boolean inErrorState;
    private TrackGroupArray lastSeenTrackGroupArray;

    private Handler mainHandler = new Handler();
    private EventLogger eventLogger;

    private SimpleExoPlayerView player_hlsPlayer;
    private TextView tv_debugInfo;

    public static Intent getIntent(Context context, String roomName) {
        return new Intent(context, HLSPlayerActivity.class)
                .putExtra(INTENT_ROOM_NAME, roomName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlsplayer);

        Intent intent = getIntent();
        if (intent != null) {
            String roomName = intent.getStringExtra(INTENT_ROOM_NAME);
            if (roomName != null) {
                hlsUri = Uri.parse(String.format(Locale.getDefault(), URI_FORMAT, roomName));
            } else {
                Toast.makeText(this, "roomName not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Intent not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        initViews();
        initTools();
        initPlayer();
        setupDataSource(hlsUri);
    }

    private void initViews() {
        player_hlsPlayer = (SimpleExoPlayerView) findViewById(R.id.player_hlsPlayer);
        tv_debugInfo = (TextView) findViewById(R.id.tv_debugInfo);
    }

    private void initTools() {
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
//        trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
        eventLogger = new EventLogger(trackSelector);
        mediaDataSourceFactory = buildDataSourceFactory(true);
    }

    private void initPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player.addListener(playerEventListener);
        player.addListener(eventLogger);
        player.setAudioDebugListener(eventLogger);
        player.setVideoDebugListener(eventLogger);
        player.setMetadataOutput(eventLogger);

        player_hlsPlayer.setPlayer(player);
        player.setPlayWhenReady(true);
        debugViewHelper = new DebugTextViewHelper(player, tv_debugInfo);
        debugViewHelper.start();
    }

    private void setupDataSource(Uri uri) {
        HlsMediaSource mediaSource = new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
        player.prepare(mediaSource, true, false);
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        DefaultBandwidthMeter bandwidthMeter = useBandwidthMeter ? BANDWIDTH_METER : null;
        return new DefaultDataSourceFactory(this, bandwidthMeter, new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "ExoPlayerDemo"), bandwidthMeter));
    }

    private Player.EventListener playerEventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }
    };
}
