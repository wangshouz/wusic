package com.wangsz.wusic.application;

import android.app.Application;

import com.wangsz.wusic.manager.MusicServiceManager;


/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class App extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        if (!MusicServiceManager.isMusicServiceProcess(sApplication)) {
            MusicServiceManager.init(sApplication);
        }

    }

    public static Application getInstance() {
        return sApplication;
    }

}
