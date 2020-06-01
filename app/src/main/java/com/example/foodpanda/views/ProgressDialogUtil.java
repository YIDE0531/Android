package com.example.foodpanda.views;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.example.foodpanda.R;

/**
 * 耗時對話方塊工具類
 */
public class ProgressDialogUtil {
    private AlertDialog mAlertDialog;

    /**
     * 彈出耗時對話方塊
     * @param context
     */
    public void showProgressDialog(Context context) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog).create();
        }

        View loadView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog_view, null);
        mAlertDialog.setView(loadView, 0, 0, 0, 0);
        mAlertDialog.setCanceledOnTouchOutside(false);

//        TextView tvTip = loadView.findViewById(R.id.tvTip);
//        tvTip.setText("載入中...");

        mAlertDialog.show();
    }

    public void showProgressDialog(Context context, String tip) {
        if (TextUtils.isEmpty(tip)) {
            tip = "載入中...";
        }

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog).create();
        }

        View loadView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog_view, null);
        mAlertDialog.setView(loadView, 0, 0, 0, 0);
        mAlertDialog.setCanceledOnTouchOutside(false);

//        TextView tvTip = loadView.findViewById(R.id.tvTip);
//        tvTip.setText(tip);

        mAlertDialog.show();
    }

    /**
     * 隱藏耗時對話方塊
     */
    public void dismiss() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
