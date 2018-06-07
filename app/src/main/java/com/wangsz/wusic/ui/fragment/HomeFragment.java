package com.wangsz.wusic.ui.fragment;

import com.wangsz.wusic.bean.SongInfo;
import com.wangsz.wusic.manager.MediaManager;
import com.wangsz.wusic.viewbinder.SongViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class HomeFragment extends BaseListFragment {

    @Override
    public void initAdapterRegister() {
        mMultiTypeAdapter.register(SongInfo.class,new SongViewBinder());
    }

    @Override
    protected void init() {
        super.init();
        getSongs();
    }

    private void getSongs() {
        mItems.addAll(MediaManager.getInstance().getSongInfoList(mContext));
        mMultiTypeAdapter.notifyDataSetChanged();
    }
}
