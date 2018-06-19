package com.wangsz.wusic.viewbinder;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangsz.wusic.R;
import com.wangsz.wusic.aidl.Song;
import com.wangsz.wusic.bean.SongInfo;
import com.wangsz.wusic.constant.Action;
import com.wangsz.wusic.manager.MusicServiceManager;

import me.drakeet.multitype.ItemViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public class SongViewBinder extends ItemViewBinder<SongInfo, SongViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SongInfo song) {
        holder.tvTitle.setText(song.getTitle());
        holder.tvDuration.setText(song.getDuration() + "ms");
        holder.itemView.setOnClickListener(v -> {
            try {
                MusicServiceManager.getPlayerInterface().action(Action.PLAY, new Song(song.getData()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDuration;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDuration = itemView.findViewById(R.id.tv_duration);
        }
    }
}
