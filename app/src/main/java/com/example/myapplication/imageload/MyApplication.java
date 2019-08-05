package com.example.myapplication.imageload;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.myapplication.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


public class MyApplication extends Application {
    @Override
    //必须重写一下onCreate
    //在manifest XML里面的<application>下面配置 启动android name改为MyApplication（默认是Application）
    public void onCreate() {
        super.onCreate();
        // 缓存图片的配置，一般通用的配置
        initImageLoader(getApplicationContext());
    }
    private void initImageLoader(Context context) {
        // 创建DisplayImageOptions对象   后期继承的类里面可以再定义
        DisplayImageOptions defaulOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_progress)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.bg_progress)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.bg_progress)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型// 推荐565
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();// 构建完成

        // 创建ImageLoaderConfiguration对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaulOptions)
                .memoryCacheExtraOptions(480, 800) // 即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量  最好在1-5之间
                .threadPriority(Thread.NORM_PRIORITY - 2)////设置同时运行的线程 默认
                .denyCacheImageMultipleSizesInMemory()//缓存显示不同大小的同一张图片
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))//图片内存最大 这设的2M 不要超过8M
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())////将图片保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(50 * 1024 * 1024))//本地SD卡最大缓存
                .discCacheFileCount(100) //缓存的文件数量
                .writeDebugLogs()//输出日志
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();//请求队列顺序


        // ImageLoader对象的配置 全局初始化配置
        ImageLoader.getInstance().init(configuration);
    }
}
