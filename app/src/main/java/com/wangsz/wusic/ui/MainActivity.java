package com.wangsz.wusic.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wangsz.wusic.R;
import com.wangsz.wusic.manager.MediaManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    private static String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable disposable = rxPermissions.requestEachCombined(mPermissionList).subscribe(permission -> {
            if (permission.granted) {
                // All permissions are granted !
                MediaManager.getInstance().refreshData(mContext);
            } else if (permission.shouldShowRequestPermissionRationale) {
                // At least one denied permission without ask never again
            } else {
                // At least one denied permission with ask never again
                // Need to go to the settings
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("权限申请")
                        .setMessage(mContext.getString(R.string.app_name) + "需要文件存储权限")
                        .setNegativeButton("去授权", (dialog1, which) -> {
//                            SettingUtil.goSetting(activity);
                        })
                        .create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
