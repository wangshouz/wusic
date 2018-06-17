package com.wangsz.wusic.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wangsz.wusic.base.BaseInterface;

import java.lang.ref.WeakReference;

public class PermissionUtil {

    private WeakReference<Activity> weakReference;
    private BaseInterface<Void> baseInterface;

    public PermissionUtil(Activity activity, BaseInterface<Void> baseInterface) {
        this.weakReference = new WeakReference<>(activity);
        this.baseInterface = baseInterface;
    }

    /**
     * 获取读写权限
     */
    @SuppressLint("CheckResult")
    public void requestStoragePermissions() {
        if (weakReference.get() != null) {
            RxPermissions rxPermissions = new RxPermissions(weakReference.get());
            rxPermissions.requestEachCombined(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            // All permissions are granted !
                            if (baseInterface != null) {
                                baseInterface.success(null);
                            }
                        } else {
                            if (baseInterface != null) {
                                baseInterface.failed(0);
                            }
                        }
                    });
        }
    }


}
