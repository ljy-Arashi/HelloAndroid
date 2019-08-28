package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
/**可以这样创建 New ---> service---->service(IntentService)
 * 但是会多出一些用不到的方法
 * */
public class MyIntentService extends IntentService {
    public MyIntentService(){
        //// 调用父类的有参构造函数
        super("MyIntentService");
    }

    /**根据IntentService的特性，这个服务在运行结束后应该是会自动停止的，
     * 所以我们又重写了onDestroy()方法，在这里也打印了一行日志，以证实服务是不是停止掉了。*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy executed");
    }

    /**在这个方法中可以去处理一些具体的逻辑，而且不用担心ANR的问题*/
    @Override
    protected void onHandleIntent(Intent intent) {
        // 打印当前线程的id
        Log.d("MyIntentService", "Thread id is " + Thread.currentThread(). getId());
    }
}
