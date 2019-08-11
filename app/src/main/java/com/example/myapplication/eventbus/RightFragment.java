package com.example.myapplication.eventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class RightFragment extends Fragment {
    private TextView mTvFgRight;
    private static final String TAG="Test";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置布局文件
        View view=inflater.inflate(R.layout.fragement_right,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvFgRight=view.findViewById(R.id.tv_fragmentRight);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * onEvent	事件处理在事件发送的那个线程执行	PostThread
     * onEventMainThread	事件在主线程-UI线程执行	MainThread
     * onEventBackgroundThread	事件在一个后台线程执行（就一个后台线程）	BackgroundThread
     * onEventAsync	事件会单独启动一个线程执行(每个事件都会启动一个线程)	Async**/

    /**
     * 订阅者将在Android的主线程（有时称为UI线程）中调用。如果发布线程是主线程，
     * 则将直接调用事件处理程序方法（与ThreadMode.POSTING所描述的同步）。
     * 使用此模式的事件处理程序必须快速返回以避免阻塞主线程。*/
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(MyEventTwo event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(event.getMessage());//对UI进行更新操作
//        Log.i(TAG,"onEventMainThread收到MyEventTwo"+msg);
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(MyEvent event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(event.getMessage());
//        Log.i(TAG,"onEventMainThread收到MyEvent"+msg);
//    }

//    /**和发布者在用一个线程,如果发布者是子线程它就是子线程，发布者是主线程它就是主线程
//     * 事件传递是同步完成的，一旦发布完成，
//     * 所有订阅者都将被调用。此ThreadMode意味着开销最小，
//     * 因为它完全避免了线程切换。因此，这是已知完成的简单任务的推荐模式，
//     * 是一个非常短的时间而不需要主线程。使用此模式的事件处理程序应该快速返回以避免阻止发布线程，
//     * 这可能是主线程。*/
//    @Subscribe(threadMode = ThreadMode.POSTING) //默认是POSTING
//    public void onEvent(MyEventTwo event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(event.getMessage());//不能在非UI线程中对UI进行更新操作 这里子线程发消息操作UI会出错
//        Log.i(TAG,"onEvent收到MyEventTwo"+msg);
//    }
//    @Subscribe(threadMode = ThreadMode.POSTING) //默认是POSTING
//    public void onEvent(MyEvent event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(event.getMessage());//不能在非UI线程(子线程)中对主线程UI进行更新操作
//        Log.i(TAG,"onEvent收到MyEvent"+msg);
//    }
//  /**订阅者将在后台线程中调用。如果发布线程不是主线程，
//     则将在发布线程中直接调用事件处理程序方法。如果发布线程是主线程，
//     则EventBus使用单个后台线程，该线程将按顺序传递其所有事件。（发布者是子线程就直接执行，如果不是子线程就重新创建一给新线程）
//     使用此模式的事件处理程序应尝试快速返回以避免阻塞后台线程。*/
//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onEventBackgroundThread(MyEventTwo event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(msg);//不能在非UI线程中对UI进行更新操作 这里主线程和子线程都会出错
//        Log.i(TAG,"onEventBackgroundThread收到MyEventTwo"+msg);
//    }
//
//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onEventBackgroundThread(MyEvent event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(msg);//不能在非UI线程中对UI进行更新操作
//        Log.i(TAG,"onEventBackgroundThread收到MyEvent"+msg);
//    }
//
//    /**
//     * 事件处理程序方法在单独的线程中调用。
//     * 这始终独立于发布线程和主线程。 *不管主线程还是子线程都给你创建一个线程
//     * 发布事件永远不会等待使用此模式的事件处理程序方法。
//     * 如果事件处理程序的执行可能需要一些时间，例如用于网络访问，则应使用此模式。
//     * 避免同时触发大量长时间运行的异步处理程序方法来限制并发线程数。
//     * EventBus使用线程池从已完成的异步事件处理程序通知中有效地重用线程。*/
//    @Subscribe(threadMode = ThreadMode.ASYNC)
//    public void onEventAsync(MyEventTwo event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(event.getMessage());//不能在非UI线程中对UI进行更新操作这里主线程和子线程都会出错
//        Log.i(TAG,"onEventAsync收到MyEventTwo"+msg);
//    }
//    @Subscribe(threadMode = ThreadMode.ASYNC)
//    public void onEventAsync(MyEvent event){
//        String msg="返回数据："+event.getMessage()
//                +"线程名称："+Thread.currentThread().getName()
//                +"线程ID"+Thread.currentThread().getId();
//        mTvFgRight.setText(event.getMessage());//不能在非UI线程中对UI进行更新操作
//        Log.i(TAG,"onEventAsync收到MyEvent"+msg);
//    }
//
//    /**
//     * 订阅者将在Android的主线程中调用。
//     * 该事件总是排队等待以后交付给订阅者，
//     * 因此对post的调用将立即返回。
//     * 这为事件处理提供了更严格且更一致的顺序（因此名称为MAIN_ORDERED）。
//     * 例如，如果您在具有MAIN线程模式的事件处理程序中发布另一个事件，
//     * 则第二个事件处理程序将在第一个事件处理程序之前完成（因为它是同步调用的-将其与方法调用进行比较）。
//     * 使用MAIN_ORDERED，第一个事件处理程序将完成，
//     * 然后第二个事件处理程序将在稍后的时间点调用（一旦主线程具有容量）
//     * 使用此模式的事件处理程序必须快速返回以避免阻塞主线程。
//*/
//    //在Android UI主线程中调用
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent(MyEventTwo event) {
        String msg="返回数据："+event.getMessage()
                +"线程名称："+Thread.currentThread().getName()
                +"线程ID"+Thread.currentThread().getId();
        mTvFgRight.setText(event.getMessage());//不能在非UI线程中对UI进行更新操作 这里和onEventMainThread一样都OK
        Log.i(TAG,"onMessageEvent--MAIN_ORDERED收到MyEvent"+msg);
    }
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent(MyEvent event) {
        String msg="返回数据："+event.getMessage()
                +"线程名称："+Thread.currentThread().getName()
                +"线程ID"+Thread.currentThread().getId();
        mTvFgRight.setText(event.getMessage());//不能在非UI线程中对UI进行更新操作
        Log.i(TAG,"onMessageEvent--MAIN_ORDERED收到MyEvent"+msg);
    }
}
