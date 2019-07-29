package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class AFragment extends Fragment {
    private TextView mTvTitle;
    private Button mBtnChange,mBtnReSet,mBtnMessage;
    private BFragment bFragment;
    private IOnMessageClick iOnMessageClick;
    //fragment传参
    public static AFragment newInstance(String title){
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);//传参用这个
        return fragment;
    }

    public interface IOnMessageClick{
        void onClick(String text);
    }

    //当Fragment被依附到Activity时候会调用这个方法，所以写在这里
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iOnMessageClick = (IOnMessageClick) context;
        }catch (ClassCastException e){
            throw  new ClassCastException("Activity 必须实现iOnMessageClick接口");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //设置布局文件
        View view=inflater.inflate(R.layout.fragement_a,container,false);
        Log.d("AFragment","-----------onCreateView--------------");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //加载布局后进行的操作
        mTvTitle=view.findViewById(R.id.tv_fga_title);
        mBtnChange=view.findViewById(R.id.btn_changeFg);
        mBtnReSet=view.findViewById(R.id.btn_reSet);
        mBtnMessage=view.findViewById(R.id.btn_message);
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bFragment==null){
                    bFragment=new BFragment();
                }

                Fragment fragment=getFragmentManager().findFragmentByTag("a");
                if (fragment!=null)
                {
                    //先隐藏前一个fragment在加载新的fragment（在前一个fragment更改的视图也会被保留），addToBackStack(null) 添加到返回栈。点返回键就会返回上个fragment
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                }else {
                    getFragmentManager().beginTransaction().replace(R.id.fl_container, bFragment).addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });

        mBtnReSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvTitle.setText("我是新的内容");
            }
        });

        mBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((ContainerActivity)getActivity()).SetData("你好"); 不推荐这样传值
                iOnMessageClick.onClick("你好");//实现回调接口来传值
            }
        });
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
