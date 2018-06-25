package com.wangsz.wusic.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.wangsz.libs.rxbus.RxBus;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.events.SongEvent;

/**
 * author: wangsz
 * date: On 2018/6/22 0022
 */
public class PlayBroadcastReceiver extends BroadcastReceiver {

    public static final String PLAY_ACTION = "com.wangsz.wusic.playaction";

    public static final String PLAY_ACTION_VALUE = "play_action_value";

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.d("收到广播");
        switch (intent.getIntExtra(PLAY_ACTION_VALUE, 0)) {
            case Action.COMPLETION:
                // 当前歌曲播放完毕
                SongControl.getInstance().next();
                break;
            case Action.STOP:
                // 手动停止播放
                // 发送消息更新底部播放UI
                RxBus.getInstance().send(new SongEvent(null,Action.STOP));
                break;
            default:
                break;
        }
    }
}
