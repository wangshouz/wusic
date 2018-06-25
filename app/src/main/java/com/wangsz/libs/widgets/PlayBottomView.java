package com.wangsz.libs.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangsz.libs.imageloader.MyImageLoader;
import com.wangsz.wusic.R;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.manager.SongControl;
import com.wangsz.wusic.ui.fragment.PlayListDialog;

/**
 * author: wangsz
 * date: On 2018/6/25 0025
 */
public class PlayBottomView {

    PlayListDialog mPlayListDialog;

    protected View mView = null;
    protected Context mContext = null;

    ImageView mIvAlbum;
    ImageView mIvPlayList;
    TextView mTvTitle;
    TextView mTvArtist;
    PlayView mPlayView;

    public PlayBottomView(Activity activity, int R_layout) {
        if (activity != null) {
            mContext = activity;
            mView = activity.findViewById(R_layout);
            myFindView();
        }
    }

    public PlayBottomView(View convertView, int R_layout) {
        if (convertView != null) {
            mContext = convertView.getContext();
            this.mView = convertView.findViewById(R_layout);
            myFindView();
        }
    }

    public PlayBottomView(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        myFindView();
    }

    public void myFindView() {
        mIvAlbum = mView.findViewById(R.id.iv_cover);
        mTvTitle = mView.findViewById(R.id.tv_title);
        mTvArtist = mView.findViewById(R.id.tv_artist);
        mPlayView = mView.findViewById(R.id.playView);
        mIvPlayList = mView.findViewById(R.id.iv_playlist);
        mIvPlayList.setOnClickListener(v -> {
            if (mPlayListDialog == null) mPlayListDialog = new PlayListDialog(mContext);

            mPlayListDialog.showList();

        });
    }

    public void updataView(DBSong song) {
        MyImageLoader.diaplayAlbum("file://" + song.getAlbum_path(), mIvAlbum);
        mTvTitle.setText(song.getTitle());
        mTvArtist.setText(song.getArtist());
        mPlayView.initAnim(song.getDuration());
        mPlayView.setOnClickPlayListener(() -> SongControl.getInstance().pause(song));
    }

    public void setVisibility(int visibility) {
        mView.setVisibility(visibility);
    }

}
