package com.wangsz.wusic.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.wangsz.greendao.gen.SheetDao;
import com.wangsz.wusic.R;
import com.wangsz.wusic.db.GreenDaoManager;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.db.model.Sheet;
import com.wangsz.wusic.manager.SongControl;
import com.wangsz.wusic.ui.fragment.dialog.ConfirmDialogFragment;
import com.wangsz.wusic.ui.fragment.dialog.EditDialogFragment;
import com.wangsz.wusic.ui.fragment.dialog.SheetDialogFragment;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public class SongViewBinder extends ItemViewBinder<DBSong, SongViewBinder.ViewHolder> {

    private int position = -1;

    private Context mContext;
    private FragmentManager mFragmentManager;

    public SongViewBinder(Context context, FragmentManager fragmentManager) {
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
    }

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

        holder.ivMore.setOnClickListener(v -> {
            showMore(holder.ivMore, song);
        });

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

    private void showMore(View view, DBSong song) {
        PopupMenu menu = new PopupMenu(mContext, view);
        //获取菜单填充器
        MenuInflater inflater = menu.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.song_operate, menu.getMenu());
        //绑定菜单项的点击事件
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.add_sheet:
                    SheetDialogFragment dialogFragment = new SheetDialogFragment();
                    dialogFragment.registerOnSheet(new SheetDialogFragment.OnSheetListener() {
                        @Override
                        public void addNewSheet() {
                            dialogFragment.dismiss();
                            EditDialogFragment.instance("请输入歌单名", (dialog, s) -> {
                                if (TextUtils.isEmpty(s)) {
                                    Toast.makeText(mContext, "名称不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Sheet sheet = new Sheet();
                                sheet.setTitle(s);
                                sheet.setData(song.getData());
                                sheet.setDate_added(System.currentTimeMillis());
                                sheet.setDate_modified(System.currentTimeMillis());
                                GreenDaoManager.getInstance().getSession().getSheetDao().insert(sheet);
                                dialog.dismiss();
                                Toast.makeText(mContext, "加入歌单成功", Toast.LENGTH_SHORT).show();
                            }).show(mFragmentManager,"add_sheet");
                        }

                        @Override
                        public void addToSheet(Sheet sheet) {
                            dialogFragment.dismiss();
                            // 存下最近播放
                            List<Sheet> sheets = GreenDaoManager.getInstance()
                                    .getSession()
                                    .getSheetDao()
                                    .queryBuilder()
                                    .where(SheetDao.Properties.Title.eq(sheet.getTitle()))
                                    .where(SheetDao.Properties.Data.eq(song.getData()))
                                    .list();
                            if (!sheets.isEmpty()) {
                                Toast.makeText(mContext, "已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Sheet sheet1 = new Sheet();
                            sheet1.setTitle(sheet.getTitle());
                            sheet1.setData(song.getData());
                            sheet1.setDate_added(sheet.getDate_added());
                            sheet1.setDate_modified(System.currentTimeMillis());
                            GreenDaoManager.getInstance().getSession().getSheetDao().insert(sheet1);
                            Toast.makeText(mContext, "加入歌单成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialogFragment.show(mFragmentManager, "add_sheet");
                    break;
                case R.id.set_ring:
                    ConfirmDialogFragment.instance("确定设置为手机铃声？",
                            (dialog, which) -> dialog.dismiss())
                            .show(mFragmentManager, "set_ring");
                    break;
                case R.id.song_detail:
                    break;
                default:
                    break;
            }
            return false;
        });
        //显示(这一行代码不要忘记了)
        menu.show();
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
