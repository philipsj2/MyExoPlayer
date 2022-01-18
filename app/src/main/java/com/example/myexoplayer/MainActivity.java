package com.example.myexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.ui.TrackNameProvider;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String VIDEO_URI =
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4";

    private static MainActivity _currentInstance;

    private ExoPlayer _player;
    private StyledPlayerView _playerView;

    private Player.Listener _playbackStateListener = new PlaybackStateListener();
    private BandwidthMeter _bandwidthMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _currentInstance = this;

        _playerView = findViewById(R.id.styledPlayerView_playerView);

        try {
            _player = new ExoPlayer
                    .Builder(this)
                    .setLooper(Looper.getMainLooper())
                    .build();

            _playerView.setPlayer(_player);

            _player.addListener(_playbackStateListener);
            _player.setMediaItem(MediaItem.fromUri(VIDEO_URI));
            _player.prepare();
            _player.setPlayWhenReady(true);

            Handler handler = new Handler();
            _bandwidthMeter = new DefaultBandwidthMeter.Builder(MainActivity.this).build();
            _bandwidthMeter.addEventListener(
                    handler,
                    (elapsedMs, bytesTransferred, bitrateEstimate) -> {
                        Log.d(TAG, "Average bitrate (bps) = " +
                                (double) (bytesTransferred * 8) / (elapsedMs / 1000));
                        Log.d(TAG, "Bitrate estimate (bps) = " + bitrateEstimate);
                    });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _player.removeListener(_playbackStateListener);
        _player.release();
    }

    private static MainActivity getInstance() {
        return _currentInstance;
    }

    private String getBitrate() {
        Format format = _player.getVideoFormat();
        TrackNameProvider trackNameProvider = new DefaultTrackNameProvider(getResources());
        return trackNameProvider.getTrackName(format);
    }

    private static class PlaybackStateListener implements Player.Listener {
        @Override
        public void onPlaybackStateChanged(int playbackState) {
            try {
                String state =
                        playbackState == ExoPlayer.STATE_IDLE ?
                                "ExoPlayer.STATE_IDLE" : (
                                playbackState == ExoPlayer.STATE_BUFFERING ?
                                        "ExoPlayer.STATE_BUFFERING" : (
                                        playbackState == ExoPlayer.STATE_READY ?
                                                "ExoPlayer.STATE_READY " : (
                                                playbackState == ExoPlayer.STATE_ENDED ?
                                                        "ExoPlayer.STATE_ENDED" : "UNKNOWN_STATE"
                                        )
                                )
                        );

                Log.d(TAG, "Current player state: " + state);
                Toast.makeText(MainActivity.getInstance(), state, Toast.LENGTH_LONG).show();

                /*String bitrate = MainActivity.getInstance().getBitrate();
                Log.d(TAG, "Bitrate: " + bitrate);
                Toast.makeText(
                        MainActivity.getInstance(), "Bitrate: " + bitrate, Toast.LENGTH_LONG)
                        .show();*/
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}