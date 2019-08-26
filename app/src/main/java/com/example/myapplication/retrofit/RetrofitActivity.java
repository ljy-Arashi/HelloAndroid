package com.example.myapplication.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
 private static final String BASE_URL="http://5d6223595f648700140606c5.mockapi.io/";
 private TextView mTv;
 private ArashiMember arashiMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        mTv=findViewById(R.id.tv_retro);
        mTv.setText("1233");
    }
    public void OnGetData(View view){
        //1、创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取接口对象
        final IArashiService arashiService=retrofit.create(IArashiService.class);
        //调用方法

        arashiService.getNormal().enqueue(new Callback<ArashiMember>() {
            @Override
            public void onResponse(Call<ArashiMember> call, Response<ArashiMember> response) {
                if (response.isSuccessful()){
                    //返回的数据
                    System.out.println("onResponse body: " + response.body().status+response.body().User+response.body().Pass);
                    arashiMember=new ArashiMember(response.body().status,response.body().User,response.body().Pass);
                    mTv.setText(arashiMember.User);
                }
            }

            @Override
            public void onFailure(Call<ArashiMember> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }

   public void OnGetDataParam(View view){
       //1、创建Retrofit实例
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       //获取接口对象
       final IArashiService arashiService=retrofit.create(IArashiService.class);
       //调用方法

       arashiService.getWithQuery("22").enqueue(new Callback<ArashiMember>() {
           @Override
           public void onResponse(Call<ArashiMember> call, Response<ArashiMember> response) {
               arashiMember=new ArashiMember(response.body().status,response.body().User,response.body().Pass);
               mTv.setText(arashiMember.User);
           }

           @Override
           public void onFailure(Call<ArashiMember> call, Throwable t) {
               System.out.println("failed");
           }
       });
   }
   public void OnGetDataWithMap(View view){
//1、创建Retrofit实例
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       //获取接口对象
       final IArashiService arashiService=retrofit.create(IArashiService.class);
       //调用方法
       Map<String,String> mapPara=new HashMap<>();
       mapPara.put("user","22");
       arashiService.getWithMap(mapPara).enqueue(new Callback<ArashiMember>() {
           @Override
           public void onResponse(Call<ArashiMember> call, Response<ArashiMember> response) {
               arashiMember=new ArashiMember(response.body().status,response.body().User,response.body().Pass);
               mTv.setText(arashiMember.User);
           }

           @Override
           public void onFailure(Call<ArashiMember> call, Throwable t) {
               System.out.println("failed");
           }
       });
   }
}
