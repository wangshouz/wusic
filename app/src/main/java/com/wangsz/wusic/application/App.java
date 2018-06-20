package com.wangsz.wusic.application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.wangsz.wusic.BuildConfig;
import com.wangsz.wusic.db.DBConstant;
import com.wangsz.wusic.db.Migration;
import com.wangsz.wusic.manager.MusicServiceManager;
import com.wangsz.wusic.utils.PropertiesUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;


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

        initLog();

        if (!MusicServiceManager.isMusicServiceProcess(sApplication)) {

            // 初始化数据库
            initDB();

            MusicServiceManager.init(sApplication);
            // 在assets文件夹中创建appkey.properties文件
            // LeanCloud初始化,参数依次为 this, AppId, AppKey
            AVOSCloud.initialize(this, PropertiesUtil.load("leancloud_appid"), PropertiesUtil.load("leancloud_appkey"));
            AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
        }

    }

    private void initDB() {
        Realm.init(sApplication);
        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .name(DBConstant.DB_NAME)
//                        .migration(new Migration())
                        .schemaVersion(DBConstant.DB_VERSION)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(configuration);

        Stetho.initialize(//Stetho初始化
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        );
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
