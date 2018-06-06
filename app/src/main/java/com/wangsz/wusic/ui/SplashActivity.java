package com.wangsz.wusic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class SplashActivity extends BaseActivity {

    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDisposable = Observable.timer(1, TimeUnit.SECONDS).subscribe(aLong -> {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
