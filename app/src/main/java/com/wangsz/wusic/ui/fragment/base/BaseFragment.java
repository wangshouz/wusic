package com.wangsz.wusic.ui.fragment.base;

import android.app.Activity;
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

    protected boolean hasLoadData = false; // 是否已加载数据

    private boolean isPrentSetVisible = false; // 标记父Fragment，用于处理多重ViewPager

    protected View mView;

    protected Context mContext;
    protected Activity mActivity;

    protected abstract int getContentViewId();

    protected abstract void init();

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，
     * 用hasLoadData保证只在Fragment第一次可见时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     */
    public abstract void loadData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("BaseFragment","onCreate");
        mContext = getActivity();
        mActivity = getActivity();
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
        // 一般是第一次打开TabActivity页面时，当前Fragment会走这里
        // 如果第二个Fragment里面也是tab，那么手动把第二个里面的第一个子Fragment设置为不可见
        if (getUserVisibleHint() && getParentFragment() != null && !getParentFragment().getUserVisibleHint()) {
            setUserVisibleHint(false);
            isPrentSetVisible = true;
        }

        if (getUserVisibleHint()) {
            if (!hasLoadData) {
                loadData();
                hasLoadData = true;
            }
            onFragmentVisibleChange(true);
        }
        super.onViewCreated(view, savedInstanceState);
//        Log.d("BaseFragment","onViewCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // setUserVisibleHint在初始化Fragment的时候也会调用，且在onCreateView之前，所以mView会为null
        // mView是否初始化标志，防止回调函数在mView为空的时候触发
        if (mView == null) {
            return;
        }

        if (!hasLoadData && isVisibleToUser) {
            hasLoadData = true;
            loadData();
        }

        onFragmentVisibleChange(isVisibleToUser);

        if (isVisibleToUser && !getChildFragmentManager().getFragments().isEmpty()) {
            for (Fragment fragment : getChildFragmentManager().getFragments()) {
                if (fragment instanceof BaseFragment) {
                    if (((BaseFragment) fragment).isPrentSetVisible) {
                        fragment.setUserVisibleHint(true);
                    }
                }
            }
        }

    }

    /**
     * fragment可见状态发生变化时的回调。处理一些UI变化等？
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mView != null) {
            mView = null;
        }
    }
}
