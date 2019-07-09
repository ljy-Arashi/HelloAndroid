package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView mImg4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        mImg4 = findViewById(R.id.iv_4);
        //加载网络图片必须要有权限 在manifest里面配置
        Glide.with(this).load("https://c-ssl.duitang.com/uploads/item/201509/25/20150925142325_yF8Tr.jpeg").into(mImg4);
    }
}
