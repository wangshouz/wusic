package com.wangsz.wusic.ui.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangsz.wusic.R;
import com.wangsz.wusic.bean.Empty;
import com.wangsz.wusic.db.model.DBSong;
import com.wangsz.wusic.manager.SongControl;
import com.wangsz.wusic.viewbinder.EmptyViewBinder;
import com.wangsz.wusic.viewbinder.SongItemBinder;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * author: wangsz
 * date: On 2018/6/25 0025
 */
public class PlayListDialog extends BottomSheetDialog {

    private View mDialogView;

    private TextView mTvStyle;
    private ImageView mIvDelete;
    private RecyclerView mRecyclerView;
    private Items mItems = new Items();
    private MultiTypeAdapter mMultiTypeAdapter;

    public PlayListDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        mDialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_palylist, null);
        mTvStyle = mDialogView.findViewById(R.id.tv_style);
        mTvStyle.setText(SongControl.getInstance().getStyle());
        mTvStyle.setOnClickListener(v -> mTvStyle.setText(SongControl.getInstance().setStyle()));
        mIvDelete = mDialogView.findViewById(R.id.iv_delete);
        mIvDelete.setOnClickListener(v -> {
            SongControl.getInstance().deleteAll();
            dismiss();
        });
        mRecyclerView = mDialogView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        mMultiTypeAdapter.register(DBSong.class, new SongItemBinder() {
            @Override
            public void onClickView() {
                super.onClickView();
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        mMultiTypeAdapter.register(Empty.class, new EmptyViewBinder());
        mRecyclerView.setAdapter(mMultiTypeAdapter);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(mDialogView);
    }

    public void showList() {
        mItems.clear();
        mItems.addAll(SongControl.getInstance().getSongList());
        mMultiTypeAdapter.notifyDataSetChanged();
        if (!isShowing()) {
            show();
        }
    }

}
