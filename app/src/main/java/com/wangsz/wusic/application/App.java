package com.wangsz.wusic.application;

import android.app.Application;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.squareup.leakcanary.LeakCanary;
import com.wangsz.wusic.BuildConfig;
import com.wangsz.wusic.db.GreenDaoManager;
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

        LeakCanary.install(this);

        sApplication = this;

        initLog();

        if (!MusicServiceManager.isMusicServiceProcess(sApplication)) {

            // 初始化数据库
            initDB();

            MusicServiceManager.init(sApplication);
        }

    }

    private void initDB() {
        GreenDaoManager.getInstance();
    }

    private void initLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                        : LogLevel.NONE)
                .tag("wusic")                                         // 指定 TAG，默认为 "X-LOG"
//                .t()                                                   // 允许打印线程信息，默认禁止
//                .st(2)                                                 // 允许打印深度为2的调用栈信息，默认禁止
//                .b()                                                   // 允许打印日志边框，默认禁止
                .build();

        XLog.init(config);
    }

    public static Application getInstance() {
        return sApplication;
    }

}
