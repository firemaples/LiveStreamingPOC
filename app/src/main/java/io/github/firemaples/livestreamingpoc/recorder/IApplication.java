package io.github.firemaples.livestreamingpoc.recorder;

import android.content.Context;
import android.content.Intent;

/**
 * Created by louis1chen on 08/09/2017.
 */

public interface IApplication {
    Intent getRTMPRecorderIntent(Context context, String roomName);
}
