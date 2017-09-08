package io.github.firemaples.livestreamingpoc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import io.github.firemaples.livestreamingpoc.player.HLSPlayerActivity;
import io.github.firemaples.livestreamingpoc.recorder.IApplication;

import static io.github.firemaples.livestreamingpoc.SettingUtil.DEFAULT_HLS_PLAYER_URL;
import static io.github.firemaples.livestreamingpoc.SettingUtil.DEFAULT_ROOM_NAME;
import static io.github.firemaples.livestreamingpoc.SettingUtil.DEFAULT_RTMP_PUBLISH_URL;
import static io.github.firemaples.livestreamingpoc.SettingUtil.KEY_HLS_PLAYER_URL;
import static io.github.firemaples.livestreamingpoc.SettingUtil.KEY_ROOM_NAME;
import static io.github.firemaples.livestreamingpoc.SettingUtil.KEY_RTMP_PUBLISH_URL;

public class MainActivity extends AppCompatActivity {

    private EditText et_rtmpPublishUrl, et_hlsPlayerUrl, et_roomName;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.bt_rtmpPublisher) {
                startActivity(((IApplication) getApplication()).getRTMPPublisherIntent(MainActivity.this, getRoomName()));
            } else if (id == R.id.bt_hlsPlayer) {
                startActivity(HLSPlayerActivity.getIntent(MainActivity.this, getRoomName()));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_rtmpPublishUrl = (EditText) findViewById(R.id.et_rtmpPublishUrl);
        et_hlsPlayerUrl = (EditText) findViewById(R.id.et_hlsPlayerUrl);
        et_roomName = (EditText) findViewById(R.id.et_roomName);
        findViewById(R.id.bt_rtmpPublisher).setOnClickListener(onClickListener);
        findViewById(R.id.bt_hlsPlayer).setOnClickListener(onClickListener);

        et_rtmpPublishUrl.setText(SettingUtil.getRTMPPublishUrl(this));
        et_hlsPlayerUrl.setText(SettingUtil.getHLSPlayerUrl(this));
        et_roomName.setText(SettingUtil.getRoomName(this));
        et_roomName.requestFocus();

        setTextChangeListener(et_rtmpPublishUrl, KEY_RTMP_PUBLISH_URL, DEFAULT_RTMP_PUBLISH_URL);
        setTextChangeListener(et_hlsPlayerUrl, KEY_HLS_PLAYER_URL, DEFAULT_HLS_PLAYER_URL);
        setTextChangeListener(et_roomName, KEY_ROOM_NAME, DEFAULT_ROOM_NAME);
    }

    private String getRoomName() {
        return et_roomName.getText().toString();
    }


    private void setTextChangeListener(final EditText editText, final String key, final String defaultValue) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ApplySharedPref")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    SettingUtil.setValue(MainActivity.this, key, s.toString());
                } else {
                    SettingUtil.setValue(MainActivity.this, key, defaultValue);
                    editText.setText(defaultValue);
                    editText.setSelection(defaultValue.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
