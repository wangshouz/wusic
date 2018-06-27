package com.wangsz.wusic.ui.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.wangsz.greendao.gen.SheetDao;
import com.wangsz.wusic.R;
import com.wangsz.wusic.db.GreenDaoManager;
import com.wangsz.wusic.db.model.Sheet;

import java.util.List;

/**
 * author: wangsz
 * date: On 2018/6/27 0027
 */
public class SheetDialogFragment extends DialogFragment {

    private OnSheetListener mOnSheetListener;
    private List<Sheet> mSheets;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSheets = GreenDaoManager.getInstance().getSession().getSheetDao().queryRaw("GROUP BY T.TITLE");
    }

    public void registerOnSheet(OnSheetListener onSheetListener) {
        this.mOnSheetListener = onSheetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BaseAlertDialog);
        builder.setTitle("请选择歌单");
        String[] strings = new String[mSheets.size() + 1];
        for (int i = 0; i < mSheets.size(); i++) {
            strings[i] = mSheets.get(i).getTitle();
        }
        strings[mSheets.size()] = "新建歌单";
        builder.setItems(strings, (dialog, which) -> {
            if (mOnSheetListener == null) return;
            if (which == mSheets.size()) {
                // new
                mOnSheetListener.addNewSheet();
            } else {
                mOnSheetListener.addToSheet(mSheets.get(which));
            }

        });
        return builder.create();
    }

    public interface OnSheetListener {

        void addNewSheet();

        void addToSheet(Sheet sheet);
    }
}
