package com.wangsz.wusic.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DBSong {

    //时间 ms
    private long duration;

    //艺术家
    private String artist;

    //所属文件夹
    private String parent_path;

    //所属专辑
    private String album;

    //专辑 ID
    private String album_id;

    //专辑图片路径
    private String album_path;

    //专辑录制时间
    private long year;

    //Path to the file on disk. 文件路径
    private String data;

    //文件大小 bytes
    private long size;

    //显示的名字
    private String display_name;

    //内容标题
    private String title;

    //文件被加入媒体库的时间
    private long date_added;

    //文件最后修改时间
    private long date_modified;

    //MIME type
    private String mime_type;

    @Generated(hash = 1191339526)
    public DBSong(long duration, String artist, String parent_path, String album,
            String album_id, String album_path, long year, String data, long size,
            String display_name, String title, long date_added, long date_modified,
            String mime_type) {
        this.duration = duration;
        this.artist = artist;
        this.parent_path = parent_path;
        this.album = album;
        this.album_id = album_id;
        this.album_path = album_path;
        this.year = year;
        this.data = data;
        this.size = size;
        this.display_name = display_name;
        this.title = title;
        this.date_added = date_added;
        this.date_modified = date_modified;
        this.mime_type = mime_type;
    }

    @Generated(hash = 1058323443)
    public DBSong() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBSong songInfo = (DBSong) o;

        return data.equals(songInfo.data);

    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public void setAlbum_path(String album_path) {
        this.album_path = album_path;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


    public void setData(String data) {
        this.data = data;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }


    public void setYear(long year) {
        this.year = year;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setDate_added(long date_added) {
        this.date_added = date_added;
    }

    public void setDate_modified(long date_modified) {
        this.date_modified = date_modified;
    }

    public long getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public long getYear() {
        return year;
    }

    public String getData() {
        return data;
    }

    public long getSize() {
        return size;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getTitle() {
        return title;
    }

    public long getDate_added() {
        return date_added;
    }

    public long getDate_modified() {
        return date_modified;
    }

    public String getMime_type() {
        return mime_type;
    }


    public String getAlbum_id() {
        return album_id;
    }


    public String getAlbum_path() {
        return album_path;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }
}
