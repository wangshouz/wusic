package com.wangsz.wusic.ui.fragment.base;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.wangsz.wusic.R;
import com.wangsz.wusic.adapter.TabAdapter;
import com.wangsz.wusic.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public abstract class BaseTabFragment extends BaseFragment {

    public abstract void initTabs();

    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected List<TabAdapter.TabClass> mTabClasses = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void init() {
        mTabLayout = mView.findViewById(R.id.tablayout);

        initTabs();

        mViewPager = mView.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new TabAdapter(getChildFragmentManager(), mTabClasses));
        mTabLayout.setupWithViewPager(mViewPager);
        reduceMarginsInTabs(mTabLayout);
    }

    /**
     * 通过反射设置tab的下划线宽度
     *
     * @param tabLayout
     */
    protected void reduceMarginsInTabs(TabLayout tabLayout) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = DisplayUtil.dip2px(mContext, 25);
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = DisplayUtil.dip2px(mContext, 25);
                }
            }
            tabLayout.requestLayout();
        }
    }

}
