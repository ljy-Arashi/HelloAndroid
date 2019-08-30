package com.example.myapplication.service.service.practice;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ServicePracticeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStart,mPause,mCancel;
    private DownloadService.DownloadBinder downloadBinder;
    //创建了一个ServiceConnection的匿名类
    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //todo
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //todo
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_practice);
        mStart=findViewById(R.id.start_download);
        mPause=findViewById(R.id.pause_download);
        mCancel=findViewById(R.id.cancel_download);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_download:
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }
}
