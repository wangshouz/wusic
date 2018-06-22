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
        this.mSongList = list;
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

    public void next() {

        if (mSongList == null || mSongList.isEmpty()) return;

        int pos = new Random().nextInt(mSongList.size());
        XLog.d("next = " + pos);
        play(mSongList.get(pos));
    }

}
