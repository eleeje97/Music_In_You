package com.example.kwons.music_in_you.Retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit 객체를 만들어줄 클래스
public class API_Client {

    // Base URL
    final static String baseUrl = "http://172.22.15.206:8000/";

    // 싱글톤
    private static API_Client api_client_instance =  new API_Client();

    // getInstance()
    public static  API_Client getApi_client_instance(){
        return api_client_instance;
    }

    // 빈생성자 생성
    private API_Client(){
    }

    // OkHttpClient 객체 생성
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build();

    // Retrofit 객체 생성
    Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();





    // API_Interface(서비스) 생성
    API_Interface api_service = retrofit.create(API_Interface.class);


    // getService()
    public API_Interface getApi_service(){
        return api_service;
    }

    // getRetrofit()
    public Retrofit getRetrofit(){
        return retrofit;
    }


}
