package com.wangsz.wusic.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: wangsz
 * date: On 2018/6/27 0027
 */
@Entity
public class RecentlySong {

    @Id
    private Long id;

    private String data;

    //文件被加入媒体库的时间
    private long date_added;

    //文件最后修改时间
    private long date_modified;

    @Generated(hash = 573478257)
    public RecentlySong(Long id, String data, long date_added, long date_modified) {
        this.id = id;
        this.data = data;
        this.date_added = date_added;
        this.date_modified = date_modified;
    }

    @Generated(hash = 220975730)
    public RecentlySong() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getDate_added() {
        return date_added;
    }

    public void setDate_added(long date_added) {
        this.date_added = date_added;
    }

    public long getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(long date_modified) {
        this.date_modified = date_modified;
    }
}
