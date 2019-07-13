package com.example.myapplication.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class MyGridViewAdapter extends BaseAdapter {

    private  Context mcontext;
    private LayoutInflater mlayoutInflater;
    public MyGridViewAdapter(Context context)
    {
        this.mcontext=context;
        mlayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder
    {
        public ImageView imageView;
        public TextView textView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null)
        {
            view=mlayoutInflater.inflate(R.layout.layout_grid_item,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=view.findViewById(R.id.iv_grid);
            viewHolder.textView=view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        }else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText("花");
        Glide.with(mcontext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563006871117&di=7da7279f3a67d707db39ad7afa51a20d&imgtype=0&src=http%3A%2F%2Fs13.sinaimg.cn%2Fmw690%2F003c59Ougy6TvR8vPYMac%26690").into(viewHolder.imageView);
        return view;
    }
}
