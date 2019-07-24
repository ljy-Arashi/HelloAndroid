package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myapplication.util.ToastUtil;

public class PopUpActivity extends AppCompatActivity {

    private Button mbtnPop;
    private PopupWindow mPop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        mbtnPop=findViewById(R.id.btn_pop);
        mbtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1=getLayoutInflater().inflate(R.layout.layout_pop,null);
                TextView textView=view1.findViewById(R.id.tv_good);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPop.dismiss();//下拉框先消失
                        ToastUtil.showMsg(PopUpActivity.this,"好");
                    }
                });
                mPop=new PopupWindow(view1,mbtnPop.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                mPop.setOutsideTouchable(true);//点击旁边会消失
                mPop.setFocusable(true);//点击后先消失再点才显示
                mPop.showAsDropDown(mbtnPop);

            }
        });
    }
}
