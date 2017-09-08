package io.github.firemaples.livestreamingpoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by louis1chen on 08/09/2017.
 */

public class SettingUtil {
    public static final String SP_NAME = "LiveStreamingPOC";
    public static final String KEY_ROOM_NAME = "roomName";
    public static final String KEY_RTMP_PUBLISH_URL = "rtmpPublishUrl";
    public static final String KEY_HLS_PLAYER_URL = "hlsPlayerUrl";

    public static final String DEFAULT_ROOM_NAME = "v2";
    public static final String DEFAULT_RTMP_PUBLISH_URL = "rtmp://192.168.1.6/live/%s";
    public static final String DEFAULT_HLS_PLAYER_URL = "http://192.168.1.6:8080/hls/%s/index.m3u8";

    public static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref")
    public static void setValue(Context context, String key, String value) {
        getSharePreferences(context).edit().putString(key, value).commit();
    }

    public static String getRoomName(Context context) {
        return getSharePreferences(context).getString(KEY_ROOM_NAME, DEFAULT_ROOM_NAME);
    }

    public static String getRTMPPublishUrl(Context context) {
        return getSharePreferences(context).getString(KEY_RTMP_PUBLISH_URL, DEFAULT_RTMP_PUBLISH_URL);
    }

    public static String getHLSPlayerUrl(Context context) {
        return getSharePreferences(context).getString(KEY_HLS_PLAYER_URL, DEFAULT_HLS_PLAYER_URL);
    }
}
