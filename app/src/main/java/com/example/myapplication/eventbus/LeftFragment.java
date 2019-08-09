package com.example.myapplication.eventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import org.greenrobot.eventbus.EventBus;


public class LeftFragment extends ListFragment {
    private static final String TAG="Test";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] strs={"主线程消息1","子线程消息1","主线程消息2"};
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,strs));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position){
            case 0:
                //主线程1
                Log.d(TAG,"------主线程1发送的消息"
                        +"TreadName:"+Thread.currentThread().getName()
                +"TreadID:"+Thread.currentThread().getId());
                EventBus.getDefault().post(new MyEvent("主线程1发送的消息"));
                break;
            case 1:
                new Thread(){
                    @Override
                    public void run() {
                        //子线程1
                        Log.d(TAG,"------子线程1发送的消息"
                                +"TreadName:"+Thread.currentThread().getName()
                                +"TreadID:"+Thread.currentThread().getId());
                        EventBus.getDefault().post(new MyEvent("子线程1发送的消息"));
                    }
                }.start();
                break;
            case 2:
                //主线程2EventBus.getDefault().post(new MyEventTwo("主线程2发送的消息"));
                Log.d(TAG,"------主线程2发送的消息"
                        +"TreadName:"+Thread.currentThread().getName()
                        +"TreadID:"+Thread.currentThread().getId());
                EventBus.getDefault().post(new MyEventTwo("主线程2发送的消息"));
                break;
        }
    }
}
