package com.example.myapplication.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

public class LinearRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRvmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_recycler_view);
        mRvmain=findViewById(R.id.rv_main);
        mRvmain.setLayoutManager(new LinearLayoutManager(LinearRecyclerViewActivity.this));
        //On Draw 先画再让你加载 OnDrawOver 你加载完再画  GetItemOffsets 在周边画
        mRvmain.addItemDecoration(new MyDecoration());
        //设置了点击事件的回调函数
        mRvmain.setAdapter(new LinearAdapter(LinearRecyclerViewActivity.this, new LinearAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(LinearRecyclerViewActivity.this, "short click.." + i, Toast.LENGTH_SHORT).show();
            }
        }, new LinearAdapter.OnItemLongClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(LinearRecyclerViewActivity.this, "long click.." + i, Toast.LENGTH_LONG).show();
            }
        }));
    }
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //每个元素下面添加了一个自定义的1dp分割线 用的颜色就是activity_linear_recycler_view.xml背景色
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }
}
