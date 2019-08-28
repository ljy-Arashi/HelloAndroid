package com.example.myapplication.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

/**任何一个服务在整个应用程序范围内都是通用的，即MyService不仅可以和MainActivity绑定，还可以和任何一个其他的活动进行绑定，而且在绑定完成后它们都可以获取到相同的DownloadBinder实例。
 * 后台服务有时候会因为内存不足被回收掉，为了防止服务被回收掉会使用前台服务
 * */
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
        Intent intent=new Intent(this,StartServiceActivity.class);
        PendingIntent pi= PendingIntent.getActivity(this,0,intent,0);
        Notification notification =new NotificationCompat.Builder(this,"default")
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                //于指定通知被创建的时间，以毫秒为单位，当下拉系统状态栏时，这里指定的时间会显示在相应的通知上。
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //通过PendingIntent构建出一个延迟执行的“意图”，当用户点击这条通知时就会执行相应的逻辑。
                .setContentIntent(pi)
                .build();
        /**在第8章中学习的创建通知的方法。只不过这次在构建出Notification对象后并没有使用NotificationManager来将通知显示出来，
         * 而是调用了startForeground()方法。这个方法接收两个参数，第一个参数是通知的id，
         * 类似于notify()方法的第一个参数，第二个参数则是构建出的Notification对象。
         * 调用startForeground()方法后就会让MyService变成一个前台服务，并在系统状态栏显示出来。*/
        startForeground(1, notification);
    }

    /**onStartCommand方法会在每次服务启动的时候调用，如果我们希望服务一旦启动就立刻去执行某个动作，就可以将逻辑写在其中*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand executed");
        /**服务中的代码都是默认运行在主线程当中的，如果直接在服务里去处理一些耗时的逻辑，就很容易出现ANR（Application Not Responding）的情况。
         所以这个时候就需要用到Android多线程编程的技术了，我们应该在服务的每个具体的方法里开启一个子线程，然后在这里去处理那些耗时的逻辑。
         因此，一个比较标准的服务就可以写成如下形式*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 处理具体的逻辑
                /**但是，这种服务一旦启动之后，就会一直处于运行状态，必须调用stopService()或者stopSelf()方法才能让服务停止下来。
                 * 所以，如果想要实现让一个服务在执行完毕后自动停止的功能，就可以这样写：*/
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**onDestory会在服务销毁的时候调用，在其中回收那些不再使用的资源*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
}
