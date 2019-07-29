package com.example.myapplication.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class ContainerActivity extends AppCompatActivity implements AFragment.IOnMessageClick {

    private AFragment aFragment;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        mTitle=findViewById(R.id.tv_container_title);
        //实例化AFragment
        aFragment =  AFragment.newInstance("我是参数");
        //把AFragment添加到Activity中  commitAllowingStateLoss比 commit宽容些不容易出错
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,aFragment,"a").commitAllowingStateLoss();
    }

    public void SetData(String text){
        mTitle.setText(text);//不推荐这种传值方法
    }

    //推荐用实现Afragment里面的回调接口来传值
    @Override
    public void onClick(String text) {
        mTitle.setText(text);
    }
}
