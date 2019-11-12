package com.example.kwons.music_in_you.Weather.Service;




import android.support.annotation.AttrRes;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresApi;

import com.example.kwons.music_in_you.Weather.Model.WeatherData;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rohit on 10/15/15.
 */
public class APIManager {

    private static Mc2Service mc2Service;
    private static final String URL = "http://api.openweathermap.org/data/2.5/";


    public interface Mc2Service {

        @GET("forecast")
        Call<WeatherData> getWeatherInfo (@Query("lat") String latitude,
                                           @Query("lon") String longitude,
                                           @Query("cnt") String cnt,
                                           @Query("appid") String appid
        );
    }

    public static Mc2Service getApiService () {


        // OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();


        Retrofit restAdapter = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mc2Service = restAdapter.create(Mc2Service.class);

        return mc2Service;


    }

}