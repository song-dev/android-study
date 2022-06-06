package com.song.androidstudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @Description: 电源键监听广播
 * @Author: songsong.chen
 * @Date: 2022/5/31 10:59
 * @Email: songsong.chen@upuphone.com
 */
public class PowerKeyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("TAG", "onReceive: " + action);
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.e("TAG", "onReceive: " + action);
        }
    }
}
