package com.example.myapplication.retrofit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
 private static final String BASE_URL="http://127.0.0.1/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }
    public void OnGetData(View view){
        //1、创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取接口对象
        IArashiService arashiService=retrofit.create(IArashiService.class);
        //调用方法 访问的链接是：/Get/getString
        arashiService.getNormal().enqueue(new Callback<ArashiMember>() {
            @Override
            public void onResponse(Call<ArashiMember> call, Response<ArashiMember> response) {
                if (response.isSuccessful()){
                    //返回的数据
                    System.out.println("onResponse body: " + response.body().name+response.body().age);
                }
            }

            @Override
            public void onFailure(Call<ArashiMember> call, Throwable t) {

            }
        });
    }
}
