package io.github.firemaples.livepoc_yasea;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import io.github.firemaples.livestreamingpoc.recorder.IApplication;

/**
 * Created by louis1chen on 08/09/2017.
 */

public class CoreApplication extends Application implements IApplication {
    @Override
    public Intent getRTMPPublisherIntent(Context context, String roomName) {
        return YaseaPublisherActivity.getIntent(context, YaseaPublisherActivity.class, roomName);
    }
}
