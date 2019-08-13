package com.example.myapplication.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.util.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpActivity extends AppCompatActivity {

    private TextView mTv_get;
    OkHttpClient okHttpClient;
    private ImageView mIv_downLoad;
    //cookie存储 OkHttp为我们提供了简便的管理方法，可自动携带，保存和更新Cookie信息
    private ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        mTv_get=findViewById(R.id.tv_http_get);
        mIv_downLoad=findViewById(R.id.iv_download);
        ////1.拿到OkHttpClient对象
        //设置超时和缓存 cookie
        File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
        int cacheSize = 10 * 1024 * 1024;

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        //可以做保存cookies操作
                        System.out.println("cookies url: " + httpUrl.toString());
//                        for (Cookie cookie : list)
//                        {
//                            System.out.println("cookies: " + cookie.toString());
//                        }
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        //加载新的cookies
                        ArrayList<Cookie> cookies = new ArrayList<>();
                        Cookie cookie = new Cookie.Builder()
                                .hostOnlyDomain(httpUrl.host())
                                .name("SESSION").value("123")
                                .build();
                        cookies.add(cookie);
                        return cookies;
                    }
                })
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                .build();




        //保存sessionID Cookies
        //okHttpClient.cookieJar().loadForRequest()
        //okHttpClient.cookieJar().saveFromResponse();
    }

    public void doDownLoad(View view){
        Request builder=new Request.Builder()
                .get()
                .url("xxxxxxx")//没有模拟的服务器
                .build();
        Call call=okHttpClient.newCall(builder);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("downLoad  onFailure ",e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            InputStream inputStream=response.body().byteStream();
            //直接加载图片到imageView里面
                //注意 如果不知道图片大小 一定要压缩
                final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIv_downLoad.setImageBitmap(bitmap);
                    }
                });
                //追踪进度
                //1.拿到总长度
                final long total=response.body().contentLength();
                long sum=0L;

            //下载图片
            int len=0;
            byte[] buf=new byte[200];
                File file=new File(Environment.getExternalStorageState(),"naruto.jpg");
                FileOutputStream fos=new FileOutputStream(file);
                while ((len=inputStream.read(buf))!=-1){
                    fos.write(buf,0,len);
                    sum+=len;
                    final long finalSum=sum;
                    //显示进度
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv_get.setText(finalSum+"/"+total);
                        }
                    });
                }
                fos.flush();
                fos.close();
                inputStream.close();
                ToastUtil.showMsg(OKHttpActivity.this,"downLoad success");

            }
        });
    }
    //直接在布局里舍得button onClick事件
    public void doGet(View view) throws IOException {
        String url = "https://www.baidu.com/";//注意加入internet权限
        //1.拿到OkHttpClient对象
        //可以设置很多属性 timeout 错误后重新执行等等
        //OkHttpClient okHttpClient=new OkHttpClient();  重量级应该放在全局 不要重新new

        //2.构造Request
        //request里面可以设置一些参数 header 通过tag cancel掉什么的
        final Request request=new Request.Builder()
                .url(url)
                .get()//默认为GET请求，可以不写
                .build();
        //3.将Request封装为call
        Call call=okHttpClient.newCall(request);
        //4.执行call
        /**
         * 注意Call#execute() 同步。这种方式会阻塞调用线程，所以在Android中应放在子线程中执行，
         * 否则有可能引起ANR异常，Android3.0 以后已经不允许在主线程访问网络。
         */

           //Response response= call.execute();//直接执行请求 通过返回的response拿到一些信息
        //异步方法 加入队列  回调  在子线程里面，不在UI线程不能控制UI
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.d("Baidu_Http  onFailure ",e.getMessage());
                e.printStackTrace();//错误堆栈信息
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String body=response.body().string();//获取的对象 注意！！！不是toString！！！
                //InputStream inputStream=response.body().byteStream();大文件就可以用流来操作
                Log.d("Baidu_Http  onResponse ",body);
                //在子线程里面，不在UI线程不能控制UI,所以要用runOnUiThread
                //mTv_get.setText(body); 在子线程里面，不在UI线程不能控制UI会报错
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv_get.setText(body);
                    }
                });

            }
        });

    }

//    public void doPost(View view){
//        /**
//         * POST请求（键值对 key-value）
//         * 1.拿到okhttpClient对象
//         * 2.创建 FormBody 添加需要的键值对
//         * 3.构造Request
//         * 4.创建一个Call对象
//         * 5.异步请求enqueue(Callback)
//         * 如果Post提交的数据是键值对就构造一个FormBody对象，可以添加N个键值对。*/
//        //2.创建 FormBody 添加需要的键值对
//        FormBody formBody = new FormBody.Builder()
//                .add("username","ljy")
//                .add("password","123")
//                .build();
//        // 3.构造Request
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url("http://www.xxxxx.com/user/login")
//                .post(formBody)//键值对
//                .build();
//        //4.创建一个Call对象
//        Call call = okHttpClient.newCall(request);
//        //5.异步请求enqueue(Callback)
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.e("post_onFailure", "登录失败="+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String json = response.body().string();
//               // UserInfo userInfo = new Gson().fromJson(json,UserInfo.class);
//               // if(userInfo!=null) {
//                    //if(userInfo.getErrorCode()!=0) {
//                       // Log.e("TAG", userInfo.getErrorMsg());
//                   // }else {
//                        //Log.e("TAG", "登录成功="+json);
//                   // }
//                //}
//            }
//        });
//    }
    public void doPost(View view){
        /**POST请求（json字符串）
         1.拿到okhttpClient对象
         2.设置提交类型MediaType+json字符串
         3.构造Request
         4.创建一个Call对象
         5.异步请求enqueue(Callback)*/
        // 1.拿到okhttpClient对象 全局

        //需要提交的json字符串
        String jsonStr = "hahaha";
        //2.创建 RequestBody 设置提交类型MediaType+json字符串
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);

        // 3.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://xxxx.com/user/login")
                .post(requestBody)//字符串
                .build();

        //4.创建一个Call对象
        Call call = okHttpClient.newCall(request);
        //5.异步请求enqueue(Callback)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("post  onFailure ",e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                //UserInfo userInfo = new Gson().fromJson(json,UserInfo.class);
                //if(userInfo!=null) {
                    //if(userInfo.getErrorCode()!=0) {
                        //Log.e("TAG", userInfo.getErrorMsg());
                  //  }else {
                        //Log.e("TAG", "登录成功="+json);
                   // }
               // }
            }
        });
    }
}
