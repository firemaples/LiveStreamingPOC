package io.github.firemaples.livestreamingpoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.github.firemaples.livestreamingpoc.player.HLSPlayerActivity;

public class MainActivity extends AppCompatActivity {

    private EditText et_roomName;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.bt_rtmpRecorder) {

            } else if (id == R.id.bt_hlsPlayer) {
                startActivity(HLSPlayerActivity.getIntent(MainActivity.this, et_roomName.getText().toString()));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_roomName = (EditText) findViewById(R.id.et_roomName);
        findViewById(R.id.bt_rtmpRecorder).setOnClickListener(onClickListener);
        findViewById(R.id.bt_hlsPlayer).setOnClickListener(onClickListener);
    }

    
}
