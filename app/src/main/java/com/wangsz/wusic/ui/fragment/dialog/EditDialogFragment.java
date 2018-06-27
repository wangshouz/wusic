package com.wangsz.wusic.ui.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.wangsz.wusic.R;

/**
 * author: wangsz
 * date: On 2018/6/27 0027
 */
public class EditDialogFragment extends DialogFragment {

    private static String mTitle = "";
    private static Callback mCallback;

    public EditDialogFragment() {
    }

    public static EditDialogFragment instance(String message, Callback callback) {
        EditDialogFragment confirmDialogFragment = new EditDialogFragment();
        mTitle = message;
        mCallback = callback;
        return confirmDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText editText = new EditText(getContext());
        editText.setBackground(null);
        editText.setPadding(60, 40, 0, 0);
        editText.setMaxEms(15);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BaseAlertDialog);
        builder.setMessage(mTitle);
        builder.setView(editText);
        builder.setNegativeButton("返回", null);
        builder.setPositiveButton("确定", (dialog, which) -> mCallback.ok(dialog, editText.getText().toString().trim()));
        return builder.create();
    }

    public interface Callback {
        void ok(DialogInterface dialog, String s);
    }
}
