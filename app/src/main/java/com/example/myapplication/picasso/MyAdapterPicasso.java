package com.example.myapplication.picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterPicasso extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mlayoutInflater;
    private List<PicassoBean> picassoBeanLists;
    public MyAdapterPicasso(Context context,List<PicassoBean> lists){
        this.mContext=context;
        this.mlayoutInflater=LayoutInflater.from(context);
        this.picassoBeanLists=lists;
    }
    @Override
    public int getCount() {
        return this.picassoBeanLists.size();
    }

    @Override
    public Object getItem(int i) {
        return this.picassoBeanLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null)
        {
            view=mlayoutInflater.inflate(R.layout.layout_picasso_list_item,null);
            holder=new ViewHolder();
            holder.ivImage=view.findViewById(R.id.iv_p);
            holder.tvTitle=view.findViewById(R.id.tv_title_p);
            holder.tvTime=view.findViewById(R.id.tv_time_p);
            view.setTag(holder);
        }else
        {
            holder= (ViewHolder) view.getTag();
        }
        //给控件赋值
        holder.tvTitle.setText(this.picassoBeanLists.get(i).getTitle());
        holder.tvTime.setText("2088-08-06");
        //图片加载方法 Picasso
        Picasso.get().load(this.picassoBeanLists.get(i).getUrl())
                .placeholder(R.drawable.loading_2)//(占位图)提供一张在网络请求还没有完成时显示的图片
                .error(R.drawable.loading_2)//error 提供一张在加载图片出错的情况下显示的默认图
                //.noPlaceholder()//这个方法的意思就是：在调用into的时候明确告诉你没有占位图设置。根据这个方法签名的解释是阻止View被回收的时候Picasso清空target或者设置一个应用的占位图。需要注意的是placeholder和noPlaceholder 不能同时应用在同一个请求上，会抛异常。
                //.noFade()//不需要 Picasso 从磁盘或者网络加载图片时，into 显示到ImageView 都会有一个简单的渐入过度效果
                .resize(200,200)//单位是pixels为了带宽、内存使用和下载速度等考虑 我们可以使用resize(int w,int hei) 来重新设置尺寸
                .centerCrop()//充满ImageView 的边界，居中裁剪
                //.centerInside()//上面的centerCrop是可能看不到全部图片的，如果你想让View将图片展示完全，可以用centerInside，但是如果图片尺寸小于View尺寸的话，是不能充满View边界的。
              /*注意：特别注意，
                1，fit 只对ImageView 有效
                2，使用fit时，ImageView 宽和高不能为wrap_content,很好理解，因为它要测量宽高。*/
                //.fit()//fit 它会自动测量我们的View的大小，然后内部调用reszie方法把图片裁剪到View的大小
                //.rotate(180);//图片旋转多少度
                //.transform()//自定义转换器 里面要new一个类 比如你要做图片高斯模糊、添加圆角、做度灰处理、圆形图片等等都可以通过Transformation来完成。
                .into(holder.ivImage);//最后把图片塞进这个ImageView里面

        return view;
    }

    //getView很重要 列表的样子
    static class ViewHolder{ //为了性能写个静态类
        public ImageView ivImage;
        public TextView tvTitle;
        public TextView  tvTime;

    }
}
