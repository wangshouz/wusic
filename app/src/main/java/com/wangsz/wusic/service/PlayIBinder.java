package com.wangsz.wusic.service;

import android.util.Log;

import com.wangsz.wusic.aidl.IPlayerInterface;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.constant.Action;

/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class PlayIBinder extends IPlayerInterface.Stub {

    private static final String TAG = PlayIBinder.class.getName();

    @Override
    public void action(int action,Song song) {
        switch (action) {
            case Action.PLAY:
                Log.d(TAG, "service onBind : PLAY");
                break;
            case Action.PAUSE:
                Log.d(TAG, "service onBind : PAUSE");
                break;
            case Action.STOP:
                Log.d(TAG, "service onBind : STOP");
                break;
        }
    }
}
