package com.wangsz.wusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;

import com.elvishew.xlog.XLog;
import com.wangsz.wusic.R;
import com.wangsz.wusic.base.BaseInterface;
import com.wangsz.wusic.bean.SongInfo;
import com.wangsz.wusic.manager.MediaManager;
import com.wangsz.wusic.ui.fragment.base.BaseListFragment;
import com.wangsz.wusic.utils.PermissionUtil;
import com.wangsz.wusic.utils.SettingUtil;
import com.wangsz.wusic.viewbinder.SongViewBinder;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class MusicListFragment extends BaseListFragment {

    private int mIntType;
    private Disposable mDisposable;

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
    protected void initAdapterRegister() {
        super.initAdapterRegister();
        mMultiTypeAdapter.register(SongInfo.class, new SongViewBinder());
    }

    @Override
    protected void init() {
        super.init();
        DividerItemDecoration decoration = new DividerItemDecoration(Objects.requireNonNull(mContext), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.item_divider_1));
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void loadData() {
        XLog.d("type = " + mIntType);
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
        mDisposable = Observable.create((ObservableOnSubscribe<List<SongInfo>>) emitter -> {
            List<SongInfo> list = MediaManager.getInstance().getSongInfoList(mContext);
            emitter.onNext(list);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songInfos -> {
                    mItems.addAll(songInfos);
                    handleData();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposable != null)
            mDisposable.dispose();
    }
}
