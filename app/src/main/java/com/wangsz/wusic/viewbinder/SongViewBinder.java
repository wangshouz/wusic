package com.wangsz.wusic.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.wangsz.wusic.R;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.manager.SongControl;

import me.drakeet.multitype.ItemViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public class SongViewBinder extends ItemViewBinder<DBSong, SongViewBinder.ViewHolder> {

    private int position = -1;

    /**
     * 更新播放列表
     */
    public void setSongList() {

    }

    public void resetPosition(int pos) {
        int oldPosition = position;
        this.position = pos;
        getAdapter().notifyItemChanged(oldPosition);
        if (position > 0)
            getAdapter().notifyItemChanged(position);
    }

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
                setSongList();
                int oldPosition = position;
                position = holder.getAdapterPosition();
                SongControl.getInstance().play(song);
                // 更新列表UI
                getAdapter().notifyItemChanged(oldPosition);
                getAdapter().notifyItemChanged(position);
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
