package com.wangsz.wusic.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.wangsz.wusic.db.model.DBSong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaManager {

    private List<DBSong> mSongs = new ArrayList<>();

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

    private List<DBSong> refreshData(Context context) {

        mSongs.clear();

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, null);
        if (cursor == null) {
            return mSongs;
        }

        while (cursor.moveToNext()) {
            DBSong song = new DBSong();
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
            File file = new File(song.getData());
            song.setParent_path(file.getParent());
            mSongs.add(song);
        }
        cursor.close();
        // 先清空数据
        return mSongs;
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
            XLog.d("cur == null");
            return null;
        }

        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            imagePath = cur.getString(0);
        }
        cur.close();
//        XLog.d("imagePath = " + imagePath);

        return imagePath;
    }

    /**
     * 获取所有本地歌曲
     *
     * @param context
     * @return
     */
    public List<DBSong> getAllSongs(Context context) {
        if (mSongs.isEmpty())
            refreshData(context);

        return mSongs;
    }
}
