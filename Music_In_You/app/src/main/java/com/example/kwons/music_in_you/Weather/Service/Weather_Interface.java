package com.example.kwons.music_in_you.Weather.Service;

import com.example.kwons.music_in_you.Weather.Model.WeatherData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Weather_Interface {



    public interface Mc2Service {

        @GET("/forecast")
        Call<ResponseBody> getWeatherInfo (@Query("lat") String latitude,
                                           @Query("lon") String longitude,
                                           @Query("cnt") String cnt,
                                           @Query("appid") String appid
                                           );
    }
}
