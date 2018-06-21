package com.wangsz.wusic.viewbinder;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.wangsz.greendao.gen.DBSongDao;
import com.wangsz.libs.rxbus.RxBus;
import com.wangsz.wusic.R;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.db.GreenDaoManager;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.events.SongEvent;
import com.wangsz.wusic.manager.MusicServiceManager;

import me.drakeet.multitype.ItemViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public class SongViewBinder extends ItemViewBinder<DBSong, SongViewBinder.ViewHolder> {

    private int position = -1;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DBSong song) {

        holder.tvTitle.setText(song.getTitle());
        holder.tvDesc.setText(String.format("%s-%s", song.getArtist(), song.getAlbum()));

        if (holder.getAdapterPosition() == position) {
            holder.ivVolume.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(v -> {
                position = holder.getAdapterPosition();
                XLog.d("toSongDetail");
            });
        } else {
            holder.ivVolume.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> {
                try {
                    int oldPosition = position;
                    position = holder.getAdapterPosition();
                    // 播放音乐
                    MusicServiceManager.getPlayerInterface().action(Action.PLAY, new Song(song.getData()));
                    // 发送消息更新底部播放UI
                    RxBus.getInstance().send(new SongEvent(song));
                    // 更新列表UI
                    getAdapter().notifyItemChanged(oldPosition);
                    getAdapter().notifyItemChanged(position);
                    // 存下最近播放
                    song.setDate_modified(System.currentTimeMillis());
                    DBSong dbSong = GreenDaoManager.getInstance().getSession().getDBSongDao().queryBuilder().where(DBSongDao.Properties.Data.eq(song.getData())).unique();
                    if (dbSong != null) {
                        dbSong.setDate_modified(System.currentTimeMillis());
                        GreenDaoManager.getInstance().getSession().getDBSongDao().update(dbSong);
                    } else {
                        GreenDaoManager.getInstance().getSession().getDBSongDao().insert(song);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivVolume;
        TextView tvTitle;
        TextView tvDesc;
        ImageView ivMore;

        ViewHolder(View itemView) {
            super(itemView);
            ivVolume = itemView.findViewById(R.id.iv_volume);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}
