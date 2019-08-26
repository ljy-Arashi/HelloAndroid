package com.example.myapplication.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IArashiService {
    //不带任何参数的 Get 请求
    @GET("liujiayan/test/Login")
    Call<ArashiMember> getNormal();

    //携带请求参数的 Get 请求
    @GET("liujiayan/test/Login")
    Call<ArashiMember> getWithQuery(@Query("user") String user);

    //携带请求参数的 Get 请求
    @GET("liujiayan/test/Login")
    Call<ArashiMember> getWithMap(@QueryMap Map<String, String> map);
//
//    //携带请求参数以及固定请求头的 Get 请求
//    @GET("Get/getString")
//    @Headers({"userName:leavesC"})
//    Call<ArashiMember> getWithQueryAndHeaders(@Query("name") String name, @Query("age") int age);
//
//    //携带请求参数以及请求头值不固定的 Get 请求
//    @GET("Get/getString")
//    Call<ArashiMember> getWithQueryAndHeader(@Header("userName") String userName, @Query("name") String name, @Query("age") int age);
//
//    //将请求值作为链接一部分的 Get 请求
//    @GET("Get/getString/{id}")
//    Call<ArashiMember> getWithPath(@Path("id") int id);

    //将请求值作为链接一部分的 Get 请求，并使用 Gson Converter
    //@GET("Get/getUser/{startId}/{number}")
    //Call<ListResponse<User>> getWithGsonConverter(@Path("startId") int startId, @Path("number") int number);
}
