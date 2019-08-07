package com.example.myapplication.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;

public class EventBusBActivity extends AppCompatActivity {

    private Button mBtnEventb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_b);
        mBtnEventb=findViewById(R.id.btn_eventB);
        mBtnEventb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //对信息发送，参数可以是object，可以封装一个类
                MyEvent myEvent=new MyEvent("EventBusBActivity被点击了");
                EventBus.getDefault().post(myEvent);
            }
        });
    }
}
