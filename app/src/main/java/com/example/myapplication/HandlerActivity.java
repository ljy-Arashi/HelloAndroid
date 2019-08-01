package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.myapplication.util.ToastUtil;

public class HandlerActivity extends AppCompatActivity {

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        //Handler 延迟3秒跳转到WebViewActivity
//        mHandler=new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(HandlerActivity.this,WebViewActivity.class);
//                startActivity(intent);
//            }
//        },3000);

        //主线程接受消息进行处理
        mHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        ToastUtil.showMsg(HandlerActivity.this,"线程通信成功");
                        break;
                }
            }
        };

        //新的线程发送消息
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message=new Message();
                message.what=1;
                mHandler.sendMessage(message);
            }
        }.start();
    }
}
