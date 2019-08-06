package com.example.myapplication.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

//适配器 表格样式
public class MyListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mlayoutInflater;
    public MyListAdapter(Context context){
        this.context=context;
        mlayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        //获取列表长度 屏幕显示几行
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

    //getView很重要 列表的样子
    static class ViewHolder{ //为了性能写个静态类
        public ImageView imageView;
        public TextView  tvTitle;
        public TextView  tvTime;
        public TextView  tvContent;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null)
        {
            view=mlayoutInflater.inflate(R.layout.layout_list_item,null);
            holder=new ViewHolder();
            holder.imageView=view.findViewById(R.id.iv);
            holder.tvTitle=view.findViewById(R.id.tv_title);
            holder.tvTime=view.findViewById(R.id.tv_time);
            holder.tvContent=view.findViewById(R.id.tv_content);
            view.setTag(holder);
        }else
        {
            holder= (ViewHolder) view.getTag();
        }
        //给控件赋值
        holder.tvTitle.setText("这是标题");
        holder.tvTime.setText("2088-08-08");
        holder.tvContent.setText("这是内容改");
        //Glide图片加载方法 类似Picasso
        Glide.with(context).load("https://c-ssl.duitang.com/uploads/item/201509/25/20150925142325_yF8Tr.jpeg").into(holder.imageView);
        return view;
    }
}
