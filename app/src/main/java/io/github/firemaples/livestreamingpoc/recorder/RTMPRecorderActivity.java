package io.github.firemaples.livestreamingpoc.recorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Locale;

import io.github.firemaples.livestreamingpoc.R;

public abstract class RTMPRecorderActivity extends Activity {

    private static final String INTENT_ROOM_NAME = "roomName";

    private static final String URI_FORMAT = "rtmp://192.168.1.6/live/%s";

    private Uri rtmpUri;

    public static Intent getIntent(Context context, Class<?> playerActivityClass, String roomName) {
        return new Intent(context, playerActivityClass)
                .putExtra(INTENT_ROOM_NAME, roomName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtmprecorder);

        Intent intent = getIntent();
        if (intent != null) {
            String roomName = intent.getStringExtra(INTENT_ROOM_NAME);
            if (roomName != null) {
                rtmpUri = Uri.parse(String.format(Locale.getDefault(), URI_FORMAT, roomName));
            } else {
                Toast.makeText(this, "roomName not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Intent not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    protected abstract void prepareRecorder(Uri uri);
}
