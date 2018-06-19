package com.wangsz.wusic.service;

import android.media.MediaPlayer;
import android.util.Log;

import com.wangsz.wusic.aidl.IPlayerInterface;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.constant.Action;

import java.io.IOException;

/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class PlayIBinder extends IPlayerInterface.Stub {

    private static final String TAG = PlayIBinder.class.getName();

    @Override
    public void action(int action, Song song) {
        switch (action) {
            case Action.PLAY:
                Log.d(TAG, "service onBind : PLAY + " + song.path);
                try {
                    MediaPlayer mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(song.path);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Action.PAUSE:
                Log.d(TAG, "service onBind : PAUSE");
                break;
            case Action.STOP:
                Log.d(TAG, "service onBind : STOP");
                break;
        }
    }
}
