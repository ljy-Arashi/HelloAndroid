package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarActivity extends AppCompatActivity {

    private ProgressBar mPb2;
    private Button mBtn,mBtnProgressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        mPb2 = findViewById(R.id.pb2);
        mBtn = findViewById(R.id.btn_progress);
        mBtnProgressDialog1=findViewById(R.id.btn_progress_dialog1);
        OnClickListener onClickListener=new OnClickListener();
        mBtn.setOnClickListener(onClickListener);
        mBtnProgressDialog1.setOnClickListener(onClickListener);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mPb2.getProgress() < 100) {
                handler.postDelayed(runnable,500);//延迟500秒执行线程
            } else {
                AlertDialog.Builder builder=new AlertDialog.Builder(ProgressBarActivity.this);
                builder.setTitle("信息").setMessage("加载完成");

            }
        }
    };
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            mPb2.setProgress(mPb2.getProgress() + 5);
            handler.sendEmptyMessage(0);//发一个空消息回去 形成循环
        }
    };

     class OnClickListener implements View.OnClickListener
     {
         @Override
         public void onClick(View view) {
             switch (view.getId()){
                 case R.id.btn_progress:
                     handler.sendEmptyMessage(0);//发空消息给handler 形成循环
                     break;
                 case R.id.btn_progress_dialog1:
                     AlertDialog.Builder alertDialog=new AlertDialog.Builder(ProgressBarActivity.this,R.style.MyCustomProgressDialog);
                     View view1= LayoutInflater.from(ProgressBarActivity.this).inflate(R.layout.layout_progress_dialog,null);
                     alertDialog.setView(view1);
                     TextView textView=view1.findViewById(R.id.myprogress_tv);
//                     alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                         @Override
//                         public void onClick(DialogInterface dialogInterface, int i) {
//                             dialogInterface.dismiss();
//                         }
//                     });
                     //alertDialog.setCancelable(false);//setCancelable(false)点旁边空白处不会消失
                     textView.setText("加载中...");
                     alertDialog.show();
                     break;
             }
         }
     }
}
