package com.wangsz.wusic.ui.fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.ViewStub;

import com.wangsz.libs.rxbus.RxBus;
import com.wangsz.libs.utils.PermissionUtil;
import com.wangsz.libs.utils.SettingUtil;
import com.wangsz.libs.widgets.PlayBottomView;
import com.wangsz.wusic.R;
import com.wangsz.wusic.base.BaseInterface;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.events.SongEvent;
import com.wangsz.wusic.manager.MediaManager;
import com.wangsz.wusic.manager.SongControl;
import com.wangsz.wusic.ui.fragment.base.BaseListFragment;
import com.wangsz.wusic.viewbinder.SongViewBinder;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class LocalMusicsFragment extends BaseListFragment {

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private SongViewBinder mSongViewBinder;

    ViewStub mViewStub;
    private PlayBottomView mPlayBottomView;

    private List<DBSong> mSongs;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_local_musics;
    }

    @Override
    protected void initAdapterRegister() {
        super.initAdapterRegister();
        mSongViewBinder = new SongViewBinder() {
            @Override
            public void setSongList() {
                super.setSongList();
                SongControl.getInstance().setSongList(mSongs);
            }
        };
        mMultiTypeAdapter.register(DBSong.class, mSongViewBinder);
    }

    @Override
    protected void init() {
        super.init();
        mViewStub = mView.findViewById(R.id.viewstub);
        mDisposable.add(RxBus.getInstance().toFlowable(SongEvent.class).subscribe(songEvent -> {

            if (songEvent.action == Action.STOP) {
                if (mPlayBottomView != null) mPlayBottomView.setVisibility(View.GONE);
                mSongViewBinder.resetPosition(-1);
                return;
            }

            if (mPlayBottomView == null) {
                mPlayBottomView = new PlayBottomView(mContext, mViewStub.inflate());
            }
            mPlayBottomView.updataView(songEvent.song);
            mPlayBottomView.setVisibility(View.VISIBLE);

            int index = mItems.indexOf(songEvent.song);
            mSongViewBinder.resetPosition(index);
            mRecyclerView.scrollToPosition(index - 3);

        }));

        DividerItemDecoration decoration = new DividerItemDecoration(Objects.requireNonNull(mContext), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.item_divider_1));
        mRecyclerView.addItemDecoration(decoration);
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
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("权限申请")
                        .setMessage(getString(R.string.app_name) + "需要文件存储权限，更好地完成分享")
                        .setNegativeButton("去授权", (dialog1, which) -> SettingUtil.goSetting(mActivity))
                        .create();
                dialog.setCancelable(true);
                dialog.show();
            }
        }).requestStoragePermissions();
    }

    private void getSongs() {
        Disposable disposable;
        disposable = Observable.create((ObservableOnSubscribe<List<DBSong>>) emitter ->
                emitter.onNext(MediaManager.getInstance().getAllSongs(mContext)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbSongs -> {
                    mSongs = dbSongs;
                    mItems.clear();
                    mItems.addAll(dbSongs);
                    handleData();
                });
        mDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null)
            mDisposable.dispose();
    }
}
