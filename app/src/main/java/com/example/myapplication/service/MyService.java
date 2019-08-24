package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
/**任何一个服务在整个应用程序范围内都是通用的，即MyService不仅可以和MainActivity绑定，还可以和任何一个其他的活动进行绑定，而且在绑定完成后它们都可以获取到相同的DownloadBinder实例。
 * 后台服务有时候会因为内存不足被回收掉，为了防止服务被回收掉会使用前台服务*/
public class MyService extends Service {
    public MyService() {
    }
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }

    }
    //在活动中指挥服务去干什么，服务就去干什么。
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** 当服务被创建时调用. */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate executed");
    }

    /**onStartCommand方法会在每次服务启动的时候调用，如果我们希望服务一旦启动就立刻去执行某个动作，就可以将逻辑写在其中*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    /**onDestory会在服务销毁的时候调用，在其中回收那些不再使用的资源*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
}
