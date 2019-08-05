package com.example.myapplication.imageload;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class UniversalImgLoaderActivity extends AppCompatActivity {

    private ImageView mIvLoad;
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        mIvLoad=findViewById(R.id.iv_ivload);
        imageLoader=ImageLoader.getInstance();

        /*
        String imageUri = "http://site.com/image.png"; // from Web  
        String imageUri = "file:///mnt/sdcard/image.png"; // from SD card  
        String imageUri = "content://media/external/audio/albumart/13"; // from content provider 服务器获取 
        String imageUri = "assets://image.png"; // from assets  
        String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)  
        * */
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_loading_image)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.bg_loading_image)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.bg_loading_image)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型// 推荐565
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();
        imageLoader.displayImage("https://s21s0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3660455785,1917099495&fm=26&gp=0.jpg", mIvLoad,options,null);
//        imageLoader.displayImage("https://s1s0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3660455785,1917099495&fm=26&gp=0.jpg", mIvLoad, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//            //开始加载
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//            //加载失败
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//            //加载完成
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                //取消加载
//            }
//        });
    }

}
