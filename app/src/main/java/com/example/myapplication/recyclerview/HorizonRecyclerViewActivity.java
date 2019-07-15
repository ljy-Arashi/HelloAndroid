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

public class HorizonRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecHorz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizon_recycler_view);
        mRecHorz=findViewById(R.id.rv_hor);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(HorizonRecyclerViewActivity.this);
        //设置一个水平排布方向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecHorz.setLayoutManager(linearLayoutManager );
        mRecHorz.addItemDecoration(new MyDecoration());
        mRecHorz.setAdapter(new HorizonAdapter(HorizonRecyclerViewActivity.this, new HorizonAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(HorizonRecyclerViewActivity.this,"short click:"+i,Toast.LENGTH_SHORT).show();
            }
        }, new HorizonAdapter.OnItemLongClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(HorizonRecyclerViewActivity.this, "long click.." + i, Toast.LENGTH_LONG).show();
            }
        }));
    }
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //每个元素下面添加了一个自定义的1dp分割线 用的颜色就是activity_linear_recycler_view.xml背景色
            //顺序：左上右下
            outRect.set(0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight),0);
        }
    }
}
