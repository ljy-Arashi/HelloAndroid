package com.example.myapplication.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class StartServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn_StartService,mBtn_StopService,mBtn_BindService,mBtn_UnBindService;

    private MyService.DownloadBinder downloadBinder;

    //创建了一个ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {
        //重写这两个方法分别会在活动与服务成功绑定以及活动与服务的连接断开的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);
        mBtn_StartService=findViewById(R.id.start_service);
        mBtn_StopService=findViewById(R.id.stop_service);
        mBtn_StartService.setOnClickListener(this);
        mBtn_StopService.setOnClickListener(this);
        mBtn_BindService = findViewById(R.id.bind_service);
        mBtn_UnBindService =  findViewById(R.id.unbind_service);
        mBtn_BindService.setOnClickListener(this);
        mBtn_UnBindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //服务的启动停止方法主要借助Intent来实现
        switch (view.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent); // 启动服务
                break;
            case R.id.stop_service:
                Intent stopIntent=new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                /**第三个参数则是一个标志位，这里传入BIND_AUTO_CREATE表示在活动和服务进行绑定后自动创建服务。这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行。*/
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE); // 绑定服务
                break;
            case R.id.unbind_service:
                /**解绑服务会执行MyService的onDestroy()方法，一个服务既调用了startService()方法，
                 * 又调用了bindService()方法的，这种情况下要同时调用stopService()和unbindService()方法，onDestroy()方法才会执行。*/
                unbindService(connection);
                break;
            default:
                break;
        }
    }
}
