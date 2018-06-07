package com.wangsz.wusic.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected View mView;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = getWindow().getDecorView();
        mContext = this;
    }
}
