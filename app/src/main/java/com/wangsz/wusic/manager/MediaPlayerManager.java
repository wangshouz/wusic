package com.wangsz.wusic.manager;

import android.content.Intent;
import android.media.MediaPlayer;

import com.elvishew.xlog.XLog;
import com.wangsz.wusic.application.App;

public class MediaPlayerManager {

    private MediaPlayer mMediaPlayer;

    private MediaPlayerManager() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(MediaPlayer::start);
        mMediaPlayer.setOnCompletionListener(mp -> {
            XLog.d("mMediaPlayer.setOnCompletionListener");
            App.getInstance().sendBroadcast(new Intent(PlayBroadcastReceiver.PLAY_ACTION));
        });
    }

    public static MediaPlayerManager getInstance() {
        return MediaPlayerManager.Holder.mediaPlayerManager;
    }

    private static class Holder {
        private static MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
    }

    public MediaPlayer get() {
        return mMediaPlayer;
    }

}
