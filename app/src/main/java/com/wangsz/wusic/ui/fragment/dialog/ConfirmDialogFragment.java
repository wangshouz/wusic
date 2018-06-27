package com.wangsz.wusic.ui.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.wangsz.wusic.R;

/**
 * author: wangsz
 * date: On 2018/6/27 0027
 */
public class ConfirmDialogFragment extends DialogFragment {

    private static String mMessage = "";
    private static DialogInterface.OnClickListener mOnClickListener;

    public ConfirmDialogFragment() {
    }

    public static ConfirmDialogFragment instance(String message, DialogInterface.OnClickListener onClickListener) {
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
        mMessage = message;
        mOnClickListener = onClickListener;
        return confirmDialogFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BaseAlertDialog);
        builder.setMessage(mMessage);
        builder.setNegativeButton("返回", null);
        builder.setPositiveButton("确定", mOnClickListener);
        return builder.create();
    }
}
