package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MyCustomerDialog extends Dialog implements View.OnClickListener {
    private TextView mTvTitle,mTvMessage,mTvCancel,mTvOk;
    private String title,message,cancel,ok;
    private IOnCancelListener cancelListener;
    private IOnOkListener okListener;
    public MyCustomerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置dialog显示居中
        setContentView(R.layout.layout_customer_dialog);

        //固定写法
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        lp.width = (int) (size.x * 0.8);// 设置dialog宽度为屏幕的宽度的80%
        getWindow().setAttributes(lp);

        mTvTitle=findViewById(R.id.tv_cd_title);
        mTvMessage=findViewById(R.id.tv_message);
        mTvCancel=findViewById(R.id.tv_cancel);
        mTvOk=findViewById(R.id.tv_ok);
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            mTvMessage.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)) {
            mTvCancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(ok)) {
            mTvOk.setText(ok);
        }
        mTvCancel.setOnClickListener(this);
        mTvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                if (cancelListener!=null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.tv_ok:
                if (okListener!=null){
                    okListener.onOk(this);
                }
                dismiss();
                break;
        }

    }

    public interface IOnCancelListener{
        void onCancel(MyCustomerDialog myCustomerDialog);
    }
    public interface IOnOkListener{
        void onOk(MyCustomerDialog myCustomerDialog);
    }

    public MyCustomerDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MyCustomerDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public MyCustomerDialog setCancel(String cancel,IOnCancelListener onCancelListener) {
        this.cancel = cancel;
        this.cancelListener=onCancelListener;
        return this;
    }

    public MyCustomerDialog setOk(String ok,IOnOkListener onOkListener) {
        this.ok = ok;
        this.okListener=onOkListener;
        return this;
    }

}
