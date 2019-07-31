package com.example.myapplication;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;
//回调的事件处理
public class MyButton extends AppCompatButton {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //触摸事件的入口方法→向下分发
    //因为没有返回true 所以会继续向下传播
    //按下dispatchTouchEvent→Listener onTouch→ MyButton的onTouchEvent→抬起dispatchTouchEvent→Listener onClick
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("MyButton","-----dispatchTouchEvent---");
        return super.dispatchTouchEvent(event); //执行两次是因为按下和抬起都执行了
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("MyButton","-----onTouchEvent---");
                break;
        }
        return super.onTouchEvent(event);
        //因为retrun false 两边都会处理，true 的话这边消费过了，就不会向其他事件(eventActivity)传播
        //但是如果是EventActivity那边设false就没用，因为是回调方法Mybutton优先，向外扩散到activity里面的回调事件
    }
}
