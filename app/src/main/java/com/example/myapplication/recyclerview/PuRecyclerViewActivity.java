package com.example.myapplication.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

public class PuRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecPu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_recycler_view);
        mRecPu=findViewById(R.id.rv_pu);
        //spanCount表示水平方向 多少行  /垂直方向多少列  后面一个参数：水平或者垂直等布局
        StaggeredGridLayoutManager sgm= new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecPu.setLayoutManager(sgm);
        mRecPu.addItemDecoration(new MyDecoration());
        mRecPu.setAdapter(new StraggeredGridAdapter(PuRecyclerViewActivity.this, new StraggeredGridAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(PuRecyclerViewActivity.this, "short click.." + i, Toast.LENGTH_SHORT).show();
            }
        }, new StraggeredGridAdapter.OnItemLongClickListener() {
            @Override
            public void OnClick(int i) {
                Toast.makeText(PuRecyclerViewActivity.this, "long click.." + i, Toast.LENGTH_LONG).show();
            }
        }));

    }
    class MyDecoration extends RecyclerView.ItemDecoration
    {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dh=getResources().getDimensionPixelOffset(R.dimen.dividerHeight2);
            outRect.set(dh,dh,dh,dh);
        }
    }
}
