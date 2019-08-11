package com.example.myapplication.okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpActivity extends AppCompatActivity {

    private TextView mTv_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        mTv_get=findViewById(R.id.tv_http_get);
    }

    //直接在布局里舍得button onClick事件
    public void doGet(View view) throws IOException {
        String url = "https://www.baidu.com/";//注意加入internet权限
        //1.拿到OkHttpClient对象
        //可以设置很多属性 timeout 错误后重新执行等等
        OkHttpClient okHttpClient=new OkHttpClient();
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
}
