package com.wangsz.wusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

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
public class MusicListFragment extends BaseListFragment {

    private int mIntType;

    public static MusicListFragment getInstance(int type) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIntType = getArguments() != null ? getArguments().getInt("type", 0) : 0;
    }

    @Override
    public void initAdapterRegister() {
        mMultiTypeAdapter.register(SongInfo.class, new SongViewBinder());
    }

    @Override
    public void loadData() {
        Log.d("type", "type = " + mIntType);
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
