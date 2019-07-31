package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.util.ToastUtil;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnEvent;
    private MyButton btnMyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mBtnEvent=findViewById(R.id.btn_event);
        btnMyButton=findViewById(R.id.btn_myButton);
        //监听优先于回调里面的回调方法onTouchEvent
        btnMyButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("Listener","-----onTouch---");
                        break;
                }
                return false;//如果return true 回调里面的事件就不会被触发
            }
        });
//        //内部类实现
//        mBtnEvent.setOnClickListener(new OnClick());
//        //匿名内部类
//        mBtnEvent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtil.showMsg(EventActivity.this,"click...匿名内部类");
//            }
//        });
//        //事件源所在类实现监听
//        mBtnEvent.setOnClickListener(this);
//        //外部类
//        mBtnEvent.setOnClickListener(new MyClickListener(EventActivity.this));
        //--注意：给同一个事件源配置多个同类型的监听器，则只有最后一个监听器才有效，其余的不执行--------------
    }

    //XML配置必须 方法名匹配 无返回值 参数有View
    public void ClickShow(View v){
        switch (v.getId()){
            case R.id.btn_event:
                ToastUtil.showMsg(EventActivity.this,"click...XML配置");
                break;
        }
    }
    //事件源所在类实现监听 implements View.OnClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_event:
                ToastUtil.showMsg(EventActivity.this,"click...事件源所在类实现监听");
                break;
        }
    }

    //内部类
    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_event:
                    ToastUtil.showMsg(EventActivity.this,"click...内部类");
                    break;
            }
        }
    }

    //回调方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("EventActivity","-----onTouchEvent---");
                break;
        }
        return false;
    }
}
