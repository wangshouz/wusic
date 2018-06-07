package com.wangsz.wusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangsz.wusic.R;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class TestFragment extends BaseFragment {

    private TextView mTvTest;

    public static TestFragment getInstance(String title) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTest.setText(getArguments() != null ? getArguments().getString("title") : "");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init() {
        mTvTest = mView.findViewById(R.id.tv_test);
    }


}
