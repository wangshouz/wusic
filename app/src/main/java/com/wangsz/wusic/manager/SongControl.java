package com.wangsz.wusic.manager;

import android.os.RemoteException;

import com.elvishew.xlog.XLog;
import com.wangsz.greendao.gen.DBSongDao;
import com.wangsz.libs.rxbus.RxBus;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.db.GreenDaoManager;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.events.SongEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * author: wangsz
 * date: On 2018/6/22 0022
 */
public class SongControl {

    private List<DBSong> mSongList;

    private DBSong mCurrentSong;

    public static SongControl getInstance() {
        return SongControl.Holder.control;
    }

    private static class Holder {
        private static SongControl control = new SongControl();
    }

    public void setSongList(List<DBSong> list) {
        this.mSongList = new ArrayList<>(list);
    }

    public List<DBSong> getSongList() {
        return mSongList;
    }

    public DBSong getCurrentSong() {
        return mCurrentSong;
    }

    public void play(DBSong dbSong) {
        mCurrentSong = dbSong;
        // 发送消息更新底部播放UI
        RxBus.getInstance().send(new SongEvent(dbSong));
        try {
            MusicServiceManager.getPlayerInterface().action(Action.PLAY, new Song(dbSong.getData()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // 存下最近播放
        DBSong song = GreenDaoManager.getInstance().getSession().getDBSongDao().queryBuilder().where(DBSongDao.Properties.Data.eq(dbSong.getData())).unique();
        if (song != null) {
            song.setDate_modified(System.currentTimeMillis());
            GreenDaoManager.getInstance().getSession().getDBSongDao().update(song);
        } else {
            dbSong.setDate_modified(System.currentTimeMillis());
            GreenDaoManager.getInstance().getSession().getDBSongDao().insert(dbSong);
        }
    }

    public void pause(DBSong dbSong) {
        try {
            MusicServiceManager.getPlayerInterface().action(Action.PAUSE, new Song(dbSong.getData()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stop(DBSong dbSong) {
        try {
            MusicServiceManager.getPlayerInterface().action(Action.STOP, new Song(dbSong.getData()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void next() {

        if (mSongList == null || mSongList.isEmpty()) return;

        switch (mDefaultStyle) {
            // 列表播放
            case 0:
                int pos = (mSongList.indexOf(mCurrentSong) + 1) % mSongList.size();
                XLog.d("next = " + pos);
                play(mSongList.get(pos));
                break;
            // 随机
            case 1:
                int pos1 = new Random().nextInt(mSongList.size());

                while (mCurrentSong == mSongList.get(pos1)) {
                    pos1 = new Random().nextInt(mSongList.size());
                }
                XLog.d("next = " + pos1);
                play(mSongList.get(pos1));
                break;
            case 2:
                play(mCurrentSong);
                break;
        }
    }

    public void delete(int index) {

        if (mSongList == null || mSongList.isEmpty()) return;

        if (mCurrentSong == mSongList.get(index)) {
            if (mDefaultStyle == 2) {
                int pos = (mSongList.indexOf(mCurrentSong) + 1) % mSongList.size();
                mCurrentSong = mSongList.get(pos);
            }
            next();
        }

        mSongList.remove(index);

    }

    public void deleteAll() {

        stop(mCurrentSong);

        mSongList.clear();
        mCurrentSong = null;

    }

    private String[] mStyles = {"列表播放", "随机播放", "单曲循环"};
    private int mDefaultStyle = 0;

    public String getStyle() {
        return mStyles[mDefaultStyle];
    }

    public String setStyle() {
        mDefaultStyle = (mDefaultStyle + 1) % mStyles.length;
        return mStyles[mDefaultStyle];
    }
}
