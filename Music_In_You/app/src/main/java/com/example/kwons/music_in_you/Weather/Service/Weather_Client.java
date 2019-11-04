package com.example.kwons.music_in_you.Weather.Service;

import com.example.kwons.music_in_you.Retrofit.API_Client;
import com.example.kwons.music_in_you.Retrofit.API_Interface;
import com.example.kwons.music_in_you.Weather.Model.Weather;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather_Client {

    private static Weather_Interface.Mc2Service mc2Service;

    // Base URL
    final static String baseUrl = "http://miyu-server-dev.ap-northeast-2.elasticbeanstalk.com/";

    // 싱글톤
    private static Weather_Client weatherClient_Instance =  new Weather_Client();

    // getInstance()
    public static Weather_Client getApi_client_instance(){
        return weatherClient_Instance;
    }

    // 빈생성자 생성
    private Weather_Client(){
    }

    // OkHttpClient 객체 생성
    /*
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build();
    */


    public static Weather_Interface.Mc2Service getApiService () {
        // Retrofit 객체 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();


        // API_Interface(서비스) 생성
        mc2Service = retrofit.create(Weather_Interface.Mc2Service.class);

        return mc2Service;

    }

    // getService()
    /*
    public API_Interface getApi_service(){
        return api_service;
    }*/
}
