package com.wangsz.musicservice.service;

import android.util.Log;

import com.wangsz.musicservice.aidl.IPlayerInterface;
import com.wangsz.musicservice.constant.Action;

/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class PlayIBinder extends IPlayerInterface.Stub {

    private static final String TAG = PlayIBinder.class.getName();

    @Override
    public void action(int action) {
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
