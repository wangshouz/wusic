package com.wangsz.wusic.ui.fragment;

import com.wangsz.wusic.base.BaseInterface;
import com.wangsz.wusic.bean.SongInfo;
import com.wangsz.wusic.manager.MediaManager;
import com.wangsz.wusic.ui.fragment.base.BaseListFragment;
import com.wangsz.wusic.utils.PermissionUtil;
import com.wangsz.wusic.utils.SettingUtil;
import com.wangsz.wusic.viewbinder.SongViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class LocalMusicsFragment extends BaseListFragment {

    @Override
    public void initAdapterRegister() {
        mMultiTypeAdapter.register(SongInfo.class, new SongViewBinder());
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void loadData() {
        new PermissionUtil(mActivity, new BaseInterface<Void>() {
            @Override
            public void success(Void o) {
                getSongs();
            }

            @Override
            public void failed(int code) {
                SettingUtil.goSetting(mActivity);
            }
        }).requestStoragePermissions();
    }

    private void getSongs() {
        mItems.addAll(MediaManager.getInstance().getSongInfoList(mContext));
        mMultiTypeAdapter.notifyDataSetChanged();
    }
}
