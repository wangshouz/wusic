package com.wangsz.wusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.elvishew.xlog.XLog;
import com.wangsz.wusic.R;
import com.wangsz.wusic.base.BaseInterface;
import com.wangsz.wusic.db.GreenDaoManager;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.manager.MediaManager;
import com.wangsz.wusic.ui.fragment.base.BaseListFragment;
import com.wangsz.libs.utils.PermissionUtil;
import com.wangsz.libs.utils.SettingUtil;
import com.wangsz.wusic.viewbinder.SongViewBinder;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
        mMultiTypeAdapter.register(DBSong.class, new SongViewBinder());
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
        if (mIntType == 1) {
            hasLoadData = false;
        }
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
        if (mIntType == 0)
            mDisposable = Observable.create((ObservableOnSubscribe<List<DBSong>>) emitter ->
                    emitter.onNext(MediaManager.getInstance().getAllSongs(mContext)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dbSongs -> {
                        mItems.clear();
                        mItems.addAll(dbSongs);
                        handleData();
                    });
        else
            mDisposable = Observable.create((ObservableOnSubscribe<List<DBSong>>) emitter ->
                    emitter.onNext(GreenDaoManager.getInstance().getSession().loadAll(DBSong.class)))
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dbSongs -> {
                        mItems.clear();
                        mItems.addAll(dbSongs);
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
