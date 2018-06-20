package com.wangsz.wusic.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.elvishew.xlog.XLog;
import com.wangsz.wusic.bean.SongInfo;
import com.wangsz.wusic.db.model.DBSong;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.realm.Realm;

public class MediaManager {

    private HashSet<SongInfo> songs;

    private static volatile MediaManager MEDIAMANAGER;

    private MediaManager() {
    }

    //传入 Application Context
    public static MediaManager getInstance() {
        if (MEDIAMANAGER == null) {
            synchronized (MediaManager.class) {
                if (MEDIAMANAGER == null)
                    MEDIAMANAGER = new MediaManager();
            }
        }
        return MEDIAMANAGER;
    }

    private HashSet<SongInfo> refreshData(Context context) {
        if (songs == null)
            songs = new HashSet<>();
        else {
            songs.clear();
        }

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, null);
        if (cursor == null) {
            return songs;
        }

        long time = System.currentTimeMillis();
        XLog.d("开始插入数据库 ======= ");
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(DBSong.class).findAll().deleteAllFromRealm();
        while (cursor.moveToNext()) {
            SongInfo song = new SongInfo();
            String album_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
            song.setAlbum_id(album_id);
            song.setAlbum_path(getAlbumArtPicPath(context, album_id));
            song.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
            song.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
            song.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
            song.setDisplay_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
            song.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)));
            song.setMime_type(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.MIME_TYPE)));
            song.setYear(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR)));
            song.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
            song.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)));
            song.setDate_added(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_ADDED)));
            song.setDate_modified(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_MODIFIED)));
            XLog.d("song = " + song.getDisplay_name() + ";" + song.getTitle());
            songs.add(song);
            realm.createObjectFromJson(DBSong.class, JSON.toJSONString(song));
        }
        cursor.close();
        XLog.d("songs = " + songs.size());
        realm.commitTransaction();
        realm.close();
        XLog.d("插入数据库结束 ======= " + (System.currentTimeMillis() - time));
        return songs;
    }

    //根据专辑 id 获得专辑图片保存路径
    private synchronized String getAlbumArtPicPath(Context context, String albumId) {

        if (TextUtils.isEmpty(albumId)) {
            return null;
        }

        String[] projection = {MediaStore.Audio.Albums.ALBUM_ART};
        String imagePath = null;
        Uri uri = Uri.parse("content://media" + MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI.getPath() + "/" + albumId);

        Cursor cur = context.getContentResolver().query(uri, projection, null, null, null);
        if (cur == null) {
            return null;
        }

        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            imagePath = cur.getString(0);
        }
        cur.close();


        return imagePath;
    }

    /**
     * 获取所有歌曲
     *
     * @param context
     * @return
     */
    public List<SongInfo> getSongInfoList(Context context) {
        check(context);

        return new ArrayList<>(songs);
    }

    private void check(Context context) {
        if (songs == null)
            refreshData(context);
    }


}
