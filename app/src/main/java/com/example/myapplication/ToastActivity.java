package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.util.ToastUtil;

public class ToastActivity extends AppCompatActivity {

    private Button mButton1,mButton2,mButton3,mButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        mButton1=findViewById(R.id.btn_toast_1);
        mButton2=findViewById(R.id.btn_toast_2);
        mButton3=findViewById(R.id.btn_toast_3);
        mButton4=findViewById(R.id.btn_toast_4);

        OnClick onClick=new OnClick();
        mButton1.setOnClickListener(onClick);
        mButton2.setOnClickListener(onClick);
        mButton3.setOnClickListener(onClick);
        mButton4.setOnClickListener(onClick);
    }

    class  OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_toast_1:
                    Toast.makeText(getApplicationContext(),"Toast 默认显示",Toast.LENGTH_LONG).show();
                    break;
                case R.id.btn_toast_2:
                    Toast toastCenter=Toast.makeText(getApplicationContext(),"Toast 居中",Toast.LENGTH_LONG);
                    toastCenter.setGravity(Gravity.CENTER,0,0);
                    toastCenter.show();
                    break;
                case R.id.btn_toast_3:
                    Toast toastCustom=new Toast(getApplicationContext());
                    LayoutInflater layoutInflater=LayoutInflater.from(ToastActivity.this);
                   View view1=  layoutInflater.inflate(R.layout.layout_toast,null);
                    ImageView imageView= view1.findViewById(R.id.iv_toast);
                    TextView textView=view1.findViewById(R.id.tv_toast);
                    imageView.setImageResource(R.drawable.icon_smile);
                    textView.setText("成功（自定义Toast）");
                   toastCustom.setView(view1);
                    toastCustom.setDuration(Toast.LENGTH_LONG);
                    toastCustom.show();
                    break;
                case R.id.btn_toast_4:
                    //多次点击以后就不会一直排队显示，因为里面static不会多次创建,并且不为空时不创建，只把文字显示最新的
                    ToastUtil.showMsg(ToastActivity.this,"包装过的TOAST");
                    break;
            }
        }
    }
}
