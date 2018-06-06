package com.wangsz.wusic.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class Song implements Parcelable {

    public String path;

    public Song(String path) {
        this.path = path;
    }

    protected Song(Parcel in) {
        this.path = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
    }
}
