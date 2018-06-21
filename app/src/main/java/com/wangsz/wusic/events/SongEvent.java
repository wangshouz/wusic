package com.wangsz.wusic.events;

import com.wangsz.wusic.db.model.DBSong;

/**
 * author: wangsz
 * date: On 2018/6/21 0021
 */
public class SongEvent {

    public DBSong song;

    public SongEvent(DBSong song) {
        this.song = song;
    }
}
