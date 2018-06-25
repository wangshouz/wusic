package com.wangsz.wusic.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangsz.wusic.R;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.manager.SongControl;

import me.drakeet.multitype.ItemViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public class SongItemBinder extends ItemViewBinder<DBSong, SongItemBinder.ViewHolder> {

    public void onClickView(){

    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_song_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DBSong song) {

        holder.tvTitle.setText(song.getTitle());

        holder.ivClear.setOnClickListener(v -> {
            SongControl.getInstance().delete(holder.getAdapterPosition());
            getAdapter().notifyItemRemoved(holder.getAdapterPosition());
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongControl.getInstance().play(song);
                onClickView();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivClear;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivClear = itemView.findViewById(R.id.iv_clear);
        }
    }
}
