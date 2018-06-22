package com.wangsz.wusic.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;

/**
 * author: wangsz
 * date: On 2018/6/22 0022
 */
public class PlayBroadcastReceiver extends BroadcastReceiver {

    public static final String PLAY_ACTION = "com.wangsz.wusic.playaction";

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.d("收到广播");
        // 当前歌曲播放完毕
        SongControl.getInstance().next();
    }
}
