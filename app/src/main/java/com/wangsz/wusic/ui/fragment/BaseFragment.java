package com.wangsz.wusic.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;

    protected Context mContext;

    protected abstract int getContentViewId();

    protected abstract void init();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("BaseFragment","onCreate");
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d("BaseFragment","onCreateView");
        if (mView == null) {
            mView = inflater.inflate(getContentViewId(), container, false);
            init();
        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.d("BaseFragment","onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.d("BaseFragment","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d("BaseFragment","onDestroy");
        if (mView != null) {
            mView = null;
        }
    }
}
