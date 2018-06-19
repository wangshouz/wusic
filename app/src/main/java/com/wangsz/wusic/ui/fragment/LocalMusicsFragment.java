package com.wangsz.wusic.ui.fragment;

import com.wangsz.wusic.R;
import com.wangsz.wusic.adapter.TabAdapter;
import com.wangsz.wusic.ui.fragment.base.BaseTabFragment;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class LocalMusicsFragment extends BaseTabFragment {

    @Override
    public void initTabs() {
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(0), getString(R.string.song)));
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(1), getString(R.string.singer)));
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(2), getString(R.string.directory)));
    }

    @Override
    public void loadData() {

    }

}
