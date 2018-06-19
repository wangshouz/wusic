package com.wangsz.wusic.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {

    private List<TabClass> data;

    public TabAdapter(FragmentManager fm, List<TabClass> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position).fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).title;
    }

    public static class TabClass {
        Fragment fragment;
        String title;

        public TabClass(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }
    }
}
