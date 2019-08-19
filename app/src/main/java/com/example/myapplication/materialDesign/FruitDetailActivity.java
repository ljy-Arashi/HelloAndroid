package com.example.myapplication.materialDesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitDetailActivity extends AppCompatActivity {
    public static final String FRUIT_NAME = "fruit_name";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        Toolbar toolbar =  findViewById(R.id.fruit_toolBar);
        CollapsingToolbarLayout collToolBarLayout=findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView =  findViewById(R.id.fruit_image_view);
        TextView fruitContentText =  findViewById(R.id.fruit_content_text);
        //需要在代码中进行申明, 将actionbar替换成toolbar, 然后我们才可以对它进行一些操作
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP/设置返回home图标 默认
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collToolBarLayout.setTitle(fruitName);
        collToolBarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));//设置标题字颜色
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent = generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);
    }
    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //处理了HomeAsUp按钮的点击事件，当点击了这个按钮时，就调用finish()方法关闭当前的活动，从而返回上一个活动。
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
