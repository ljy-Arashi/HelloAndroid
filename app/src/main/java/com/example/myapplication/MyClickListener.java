package com.example.myapplication;

import android.app.Activity;
import android.view.View;

import com.example.myapplication.util.ToastUtil;

public class MyClickListener implements View.OnClickListener {
    private Activity mActivity;
    public MyClickListener(Activity activity){
        this.mActivity=activity;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_event:
                ToastUtil.showMsg(this.mActivity, "click....外部类实现监听");
                break;
        }
    }
}
