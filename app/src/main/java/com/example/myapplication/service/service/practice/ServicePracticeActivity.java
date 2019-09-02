package com.example.myapplication.service.service.practice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class ServicePracticeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStart,mPause,mCancel;
    private DownloadService.DownloadBinder downloadBinder;
    //创建了一个ServiceConnection的匿名类 有了这个实例，我们就可以在活动中调用服务提供的各种方法了。
    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_practice);
        mStart=findViewById(R.id.start_download);
        mPause=findViewById(R.id.pause_download);
        mCancel=findViewById(R.id.cancel_download);
        mStart.setOnClickListener(this);
        mPause.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        //启动服务可以保证DownloadService一直在后台运行，绑定服务则可以让ServicePracticeActivity和DownloadService进行通信
        Intent startIntent = new Intent(this, DownloadService.class);
        startService(startIntent); // 启动服务
        bindService(startIntent,connection,BIND_AUTO_CREATE);// 绑定服务
        /**
         * 检查指定的权限是否授权(ContextCompat对象调用)
         * 进行WRITE_EXTERNAL_STORAGE的运行时权限申请，因为下载文件是要下载到SD卡的Download目录下的，
         * 如果没有这个权限的话，我们整个程序都无法正常工作。
         */
        if (ContextCompat.checkSelfPermission(ServicePracticeActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            /**
             * 申请指定的权限(Activity或者Fragment对象调用)
             * @param permissions 权限列表，可以同时申请多个权限
             * @param requestCode 该次权限申请对应的requestCode。和 onRequestPermissionsResult()回调函数里面的requestCode对应
             */
            ActivityCompat.requestPermissions(ServicePracticeActivity.this, new
                    String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        }
    }

    /**
     * 处理请求权限的响应，当用户对请求权限的dialog做出响应之后,系统会回调该函数(Activity或者Fragment中重写)
     * @param requestCode 申请权限对应的requestCode
     * @param permissions 权限列表
     * @param grantResults 权限列表对应的返回值，判断permissions里面的每个权限是否申请成功
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    /**对服务进行解绑，不然就有可能会造成内存泄漏*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_download:
                String url = "https://g37.gdl.netease.com/onmyoji_setup_9.7.0.zip";
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
