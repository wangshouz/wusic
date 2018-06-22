package com.wangsz.wusic.service;

import android.util.Log;

import com.wangsz.wusic.aidl.IPlayerInterface;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.manager.MediaPlayerManager;

import java.io.IOException;

/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class PlayIBinder extends IPlayerInterface.Stub {

    private static final String TAG = PlayIBinder.class.getName();

//    private static MediaPlayer mMediaPlayer = new MediaPlayer();

    private Song mCurrentSong;

    @Override
    public void action(int action, Song song) {
        switch (action) {
            case Action.PLAY:
                Log.d(TAG, "service onBind : PLAY + " + song.path);
                play(song);
                break;
            case Action.PAUSE:
                Log.d(TAG, "service onBind : PAUSE");
                pause();
                break;
            case Action.STOP:
                Log.d(TAG, "service onBind : STOP");
                break;
        }
    }

    private void play(Song song) {

        // 播放当前歌曲
        if (mCurrentSong != null && song.path.equals(mCurrentSong.path)) {
            return;
        }

        mCurrentSong = song;

        try {
            MediaPlayerManager.getInstance().get().reset();
            MediaPlayerManager.getInstance().get().setDataSource(song.path);
            MediaPlayerManager.getInstance().get().prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 暂停或播放
    private void pause() {

        if (MediaPlayerManager.getInstance().get().isPlaying())
            MediaPlayerManager.getInstance().get().pause();
        else
            MediaPlayerManager.getInstance().get().start();

    }
}
