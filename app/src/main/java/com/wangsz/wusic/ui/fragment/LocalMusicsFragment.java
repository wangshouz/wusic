package com.wangsz.wusic.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.wangsz.libs.imageloader.MyImageLoader;
import com.wangsz.libs.rxbus.RxBus;
import com.wangsz.wusic.R;
import com.wangsz.wusic.adapter.TabAdapter;
import com.wangsz.wusic.events.SongEvent;
import com.wangsz.wusic.ui.fragment.base.BaseTabFragment;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class LocalMusicsFragment extends BaseTabFragment {

    private Disposable mDisposable;

    ImageView mIvAlbum;
    TextView mTvTitle;
    TextView mTvArtist;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_local_musics;
    }

    @Override
    public void initTabs() {
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(0), getString(R.string.song)));
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(1), getString(R.string.singer)));
        mTabClasses.add(new TabAdapter.TabClass(MusicListFragment.getInstance(2), getString(R.string.directory)));
    }

    @Override
    protected void init() {
        super.init();
        mIvAlbum = mView.findViewById(R.id.iv_cover);
        mTvTitle = mView.findViewById(R.id.tv_title);
        mTvArtist = mView.findViewById(R.id.tv_artist);

        mDisposable = RxBus.getInstance().toFlowable(SongEvent.class).subscribe(songEvent -> {
            MyImageLoader.diaplayAlbum("file://" + songEvent.song.getAlbum_path(), mIvAlbum);
            mTvTitle.setText(songEvent.song.getTitle());
            mTvArtist.setText(songEvent.song.getArtist());
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
