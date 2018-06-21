package com.wangsz.wusic.ui.fragment;

import android.os.RemoteException;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangsz.libs.imageloader.MyImageLoader;
import com.wangsz.libs.rxbus.RxBus;
import com.wangsz.libs.widgets.PlayView;
import com.wangsz.wusic.R;
import com.wangsz.wusic.adapter.TabAdapter;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.events.SongEvent;
import com.wangsz.wusic.manager.MusicServiceManager;
import com.wangsz.wusic.ui.fragment.base.BaseTabFragment;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class LocalMusicsFragment extends BaseTabFragment {

    private Disposable mDisposable;

    ViewStub mViewStub;
    View mViewBottom;
    ImageView mIvAlbum;
    TextView mTvTitle;
    TextView mTvArtist;
    PlayView mPlayView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_local_musics;
    }

    @Override
    public void initTabs() {
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(0), getString(R.string.song)));
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(1), getString(R.string.recently)));
    }

    @Override
    protected void init() {
        super.init();
        mViewStub = mView.findViewById(R.id.viewstub);

        mDisposable = RxBus.getInstance().toFlowable(SongEvent.class).subscribe(songEvent -> {
            if (mViewBottom == null) {
                mViewBottom = mViewStub.inflate();
                mIvAlbum = mViewBottom.findViewById(R.id.iv_cover);
                mTvTitle = mViewBottom.findViewById(R.id.tv_title);
                mTvArtist = mViewBottom.findViewById(R.id.tv_artist);
                mPlayView = mViewBottom.findViewById(R.id.playView);
                mViewBottom.setVisibility(View.VISIBLE);
            }
            MyImageLoader.diaplayAlbum("file://" + songEvent.song.getAlbum_path(), mIvAlbum);
            mTvTitle.setText(songEvent.song.getTitle());
            mTvArtist.setText(songEvent.song.getArtist());
            mPlayView.initAnim(songEvent.song.getDuration());
            mPlayView.setOnClickPlayListener(() -> {
                try {
                    MusicServiceManager.getPlayerInterface().action(Action.PAUSE, new Song(songEvent.song.getData()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null)
            mDisposable.dispose();
    }
}
