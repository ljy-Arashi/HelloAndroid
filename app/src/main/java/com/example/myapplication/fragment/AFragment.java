package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class AFragment extends Fragment {
    private TextView mTvTitle;
    //fragment传参
    public static AFragment newInstance(String title){
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);//传参用这个
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //设置布局文件
        View view=inflater.inflate(R.layout.fragement_a,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //加载布局后进行的操作
        mTvTitle=view.findViewById(R.id.tv_fga_title);
        if (getArguments()!=null){
            mTvTitle.setText(getArguments().getString("title"));
        }
        //万一Activity可能为Null 所以要判断一下
//        if (getActivity()!=null){
//            //todo..
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消异步 没做完的任务全部取消，万一Activity已经没了 所以要判断一下
    }
}
