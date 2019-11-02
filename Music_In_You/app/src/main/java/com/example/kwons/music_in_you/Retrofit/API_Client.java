package com.example.kwons.music_in_you.Retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit 객체를 만들어줄 클래스
public class API_Client {

    // Base URL
    final static String baseUrl = "http://miyu-server-dev.ap-northeast-2.elasticbeanstalk.com/";

    // 싱글톤
    private static API_Client api_client_instance =  new API_Client();

    // getInstance()
    public static  API_Client getApi_client_instance(){
        return api_client_instance;
    }

    // 빈생성자 생성
    private API_Client(){
    }

    // Retrofit 객체 생성
    Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

    // API_Interface(서비스) 생성
    API_Interface api_service = retrofit.create(API_Interface.class);


    // getService()
    public API_Interface getApi_service(){
        return api_service;
    }

}
