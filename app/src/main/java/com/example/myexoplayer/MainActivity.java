package com.example.myexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String VIDEO_URI = "https://lxxx.ceffdffbdf.xyz/?file=M3R4SUNiN3JsOHJ6WWQ2a3NQS1Y5ZGlxVlZIOCtyaGh2WUYvNVRSbUlQc0pnOXBxanNYbGU1MEtDL0ZLNzY3blNJWVIwR3VkVUlqT2FTek1rODVqR2lyRXJvNTRuR3FLMGQ1bGUrbzBjMDY5dWNEbXoya3pyRk93WElDSVRad1RTQ0krZ2hRbWlnRFc2YVNSbUVDOWtWbm9naFhHVGdKUDZ4bE9HS3lWcU1jTjl6K09TcUNoaThsQTlYN2F4OHdNK05UWTdWbXo1dWQ3bVlRd0IyY3hWc1lZZ2NPd2hxaUpnQkpjb3NsWitHN3Y0cWVEVU50dFNPREdYM0pWYTJjb3Zhck9DMTQ3bW1CR29DbWZyK29BdUNWUVZPVXN5eWVSckxmTFAzcTZMSWZURnRUZERxMmM5SURjdDd3NW5BU1o4NmlNcUloQmtnR3pRY2Z4UjkxRGdVNHRwTGVYNnNJemlVR3d6Z2RSb09rT3hYU3RKeFJ0WHRsR015TkpJY1ViQWhFSXF0bnF0T051MjRaRGUxajI0YmwwSmR3d3ZjcU9rNXhOZ255a3B0d1ZCbGp6NzBLSFJkdkJEVFVuNGlZZFZ5aUJVRXBCMEZkVVFnVGttb2QrMzJlOUI0UW9lRzRiMUdIdUVsSFhXdVVwWUt1YW5vemRNWDZCNjRRRXg0TW5RMTVUeXIrTg%3D%3D";

    private ExoPlayer _player;
    private StyledPlayerView _playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _playerView = findViewById(R.id.styledPlayerView_playerView);

        try {
            _player = new ExoPlayer
                    .Builder(this)
                    .setLooper(Looper.getMainLooper())
                    .build();

            _playerView.setPlayer(_player);

            _player.setMediaItem(MediaItem.fromUri(VIDEO_URI));
            _player.prepare();
            _player.setPlayWhenReady(true);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _player.release();
    }
}