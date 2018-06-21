package com.wangsz.libs.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.wangsz.wusic.application.App;

/**
 * author: wangsz
 * date: On 2018/5/31 0031
 */
public class SettingUtil {

    public static final int CODE_TO_SETTING = 0x123;

    public static void goSetting(Activity activity) {
        try {
            Uri packageURI = Uri.parse("package:" + App.getInstance().getPackageName());
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            activity.startActivityForResult(intent, CODE_TO_SETTING);
        } catch (Exception e) {
            Toast.makeText(App.getInstance(), "自动跳转失败，请前往设置页面手动授权", Toast.LENGTH_SHORT).show();
        }
    }

}
