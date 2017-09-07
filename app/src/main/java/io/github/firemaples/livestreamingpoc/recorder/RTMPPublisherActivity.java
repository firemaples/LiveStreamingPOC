package io.github.firemaples.livestreamingpoc.recorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import io.github.firemaples.livestreamingpoc.R;

public abstract class RTMPPublisherActivity extends AppCompatActivity {

    private static final String INTENT_ROOM_NAME = "roomName";

    private static final String URI_FORMAT = "rtmp://192.168.1.6/live/%s";

    private TextView tv_url;

    private String roomName;

    public static Intent getIntent(Context context, Class<?> playerActivityClass, String roomName) {
        return new Intent(context, playerActivityClass)
                .putExtra(INTENT_ROOM_NAME, roomName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtmppublisher);

        Intent intent = getIntent();
        if (intent != null) {
            roomName = intent.getStringExtra(INTENT_ROOM_NAME);
            if (roomName != null) {
                String rtmpUrl = String.format(Locale.getDefault(), URI_FORMAT, roomName);
                setViews(rtmpUrl);
                prepareRecorder(rtmpUrl);
            } else {
                Toast.makeText(this, "roomName not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Intent not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    protected String getRoomName() {
        return roomName;
    }

    private void setViews(String rtmpUrl) {
        tv_url = (TextView) findViewById(R.id.tv_url);
        tv_url.setText(rtmpUrl);
    }

    protected abstract void prepareRecorder(String rtmpUrl);
}
