package com.example.myapplication.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

public class GridRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler_view);
        mRecGrid=findViewById(R.id.rv_grid);
        //一行展示几个 3个 区别在new的Manager类型不一样
        GridLayoutManager gridLayoutManager=new GridLayoutManager(GridRecyclerViewActivity.this,3);
        mRecGrid.setLayoutManager(gridLayoutManager);

        mRecGrid.addItemDecoration(new MyDecoration());
        mRecGrid.setAdapter(new GridAdapter(GridRecyclerViewActivity.this, new GridAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(GridRecyclerViewActivity.this, "short click.." + i, Toast.LENGTH_SHORT).show();
            }
        }, new GridAdapter.OnItemLongClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(GridRecyclerViewActivity.this, "long click.." + i, Toast.LENGTH_LONG).show();
            }
        }));
    }
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //每个元素下面添加了一个自定义的1dp分割线 用的颜色就是activity_linear_recycler_view.xml背景色
            //顺序：左上右下
            outRect.set(getResources().getDimensionPixelOffset(R.dimen.dividerHeight),getResources().getDimensionPixelOffset(R.dimen.dividerHeight),getResources().getDimensionPixelOffset(R.dimen.dividerHeight),getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }
}
