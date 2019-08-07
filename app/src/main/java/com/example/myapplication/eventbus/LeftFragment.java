package com.example.myapplication.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;


public class LeftFragment extends ListFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] strs={"主线程消息1","子线程消息2"};
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,strs));
    }
}
