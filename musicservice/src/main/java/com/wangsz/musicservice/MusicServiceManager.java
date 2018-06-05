package com.wangsz.musicservice;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.wangsz.musicservice.aidl.IPlayerInterface;
import com.wangsz.musicservice.service.PlayService;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class MusicServiceManager {

    private static IPlayerInterface mPlayerInterface;
    private static boolean mServiceLinked = false;

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(final Context context) {
        Intent intent = new Intent(context, PlayService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mPlayerInterface = IPlayerInterface.Stub.asInterface(service);
                mServiceLinked = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                init(context);
            }
        }, BIND_AUTO_CREATE);
    }

    public static IPlayerInterface getPlayerInterface(){
        if (mPlayerInterface == null) {
            throw new RuntimeException("mPlayerInterface == null");
        }
        return mPlayerInterface;
    }

    /**
     * 判断是否是播放进程
     * @param context
     * @return
     */
    public static boolean isMusicServiceProcess(Context context) {
        int pid = android.os.Process.myPid();
        String process = getAppNameByPID(context, pid);
        return !TextUtils.isEmpty(process) && process.contains(":music");
    }

    /**
     * 根据Pid得到进程名
     */
    private static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }
}
