package com.example.kwons.music_in_you.Retrofit;


import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI_Interface {


    @GET("weather?")
    Call<WeatherTest> getCurrentWeather(@Query("lat") String lat,
                                         @Query("lon") String lon,
                                         @Query("APPID") String APPID);


}
