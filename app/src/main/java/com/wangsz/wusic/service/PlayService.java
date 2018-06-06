package com.wangsz.wusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class PlayService extends Service {

    private static final String TAG = PlayService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
    }

    @Override
    public IBinder onBind(final Intent intent) {
        Log.d(TAG, "service onBind");
        return new PlayIBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "service onDestroy");
        super.onDestroy();
    }

}
