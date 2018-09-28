package cn.bashiquan.cmj.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bashiquan.cmj.R;


/**
 * Created by mo on 2018/8/28.
 */

public class MyDialog extends Dialog {


    protected MyDialog(Context context) {
        super(context);
    }

    // 通用弹框 内容不居中
    protected MyDialog(Context context, int theme, String content, String title, String confirm, String cancle, final DialogListener dialogListener) {
        super(context, theme);

        setContentView(R.layout.dialog_detal);
        EditText mContentTv = (EditText) findViewById(R.id.content_tv);
        TextView mCancel = (TextView) findViewById(R.id.cancel_tv);
        TextView mConfirmTv = (TextView) findViewById(R.id.confirm_tv);
        TextView title_tv = (TextView) findViewById(R.id.title_tv);
        mContentTv.setText(content);
        mContentTv.setFocusable(false);
        if (TextUtils.isEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setText(title);
        }

        if (TextUtils.isEmpty(cancle)) {
            mCancel.setVisibility(View.GONE);
        } else {
            mCancel.setText(cancle);
        }

        if (TextUtils.isEmpty(confirm)) {
            mConfirmTv.setVisibility(View.GONE);
        } else {
            mConfirmTv.setText(confirm);
        }

        mConfirmTv.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogListener.onSelect();
                dismiss();
            }

        });
        mCancel.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogListener.onCancle();
                dismiss();
            }
        });
    }

    // 通用弹框 内容居中
    protected MyDialog(Context context, String content, String title, String confirm, String cancle, final DialogListener dialogListener) {
        super(context, R.style.Dialog);

        setContentView(R.layout.dialog_detal2);
        EditText mContentTv = (EditText) findViewById(R.id.content_tv);
        TextView mCancel = (TextView) findViewById(R.id.cancel_tv);
        TextView mConfirmTv = (TextView) findViewById(R.id.confirm_tv);
        TextView title_tv = (TextView) findViewById(R.id.title_tv);
        mContentTv.setText(content);
        mContentTv.setFocusable(false);
        if (TextUtils.isEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setText(title);
        }

        if (TextUtils.isEmpty(cancle)) {
            mCancel.setVisibility(View.GONE);
        } else {
            mCancel.setText(cancle);
        }

        if (TextUtils.isEmpty(confirm)) {
            mConfirmTv.setVisibility(View.GONE);
        } else {
            mConfirmTv.setText(confirm);
        }

        mConfirmTv.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialogListener != null) dialogListener.onSelect();
                dismiss();
            }

        });
        mCancel.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialogListener != null) dialogListener.onCancle();
                dismiss();
            }
        });
    }





    //通用带标题的dialog 内容不居中
     public static void showDialogDetal(Context context, String content, String title, String confirm, String cancle, boolean isCancel, DialogListener myDialogListener) {
        final MyDialog dialogManager = new MyDialog(context,
                R.style.Dialog,content,title,confirm,cancle ,myDialogListener);
        dialogManager.setCanceledOnTouchOutside(isCancel);
        dialogManager.setCancelable(isCancel);
        dialogManager.show();
    }

    //通用带标题的dialog 内容区域固定大  内容居中
    public static void showDialogDetal2(Context context, String content, String title, String confirm, String cancle, boolean isCancel, DialogListener myDialogListener) {
        final MyDialog dialogManager = new MyDialog(context,content,title,confirm,cancle ,myDialogListener);
        dialogManager.setCanceledOnTouchOutside(isCancel);
        dialogManager.setCancelable(isCancel);
        dialogManager.show();
    }

}
