package com.example.myapplication.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusAActivity extends AppCompatActivity {

    private Button mBtnEva;
    private TextView mTvEva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_a);
        //接收EventBusBActivity发回来的信息
        //注册一下
        EventBus.getDefault().register(this);

        mBtnEva=findViewById(R.id.btn_eventA);
        mTvEva=findViewById(R.id.tv_eventA);
        mBtnEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EventBusAActivity.this,EventBusBActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //活动销毁的时候要取消注册
        EventBus.getDefault().unregister(this);
    }

    //3.0官方规定的名字   4个方法里的一个，会自动接收返回数据
    /**
     * onEvent	事件处理在事件发送的那个线程执行	PostThread
     * onEventMainThread	事件在主线程-UI线程执行	MainThread
     * onEventBackgroundThread	事件在一个后台线程执行（就一个后台线程）	BackgroundThread
     * onEventAsync	事件会单独启动一个线程执行(每个事件都会启动一个线程)	Async**/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MyEvent event){
        String msg="返回数据："+event.getMessage();
        //显示在TextView里
        mTvEva.setText(msg);
    }
}
