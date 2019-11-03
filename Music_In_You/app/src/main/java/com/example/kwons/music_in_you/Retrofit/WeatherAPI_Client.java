package com.example.kwons.music_in_you.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPI_Client {


    final static String baseUrl = "http://api.openweathermap.org/data/2.5/";

    // 싱글톤
    private static WeatherAPI_Client weatherAPIClient = new WeatherAPI_Client();

    // getInstance()
    public static  WeatherAPI_Client getWeatherAPIClient(){
        return weatherAPIClient;
    }

    // 빈생성자 생성
    private WeatherAPI_Client(){
    }


    // Retrofit 객체 생성
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    // API_Interface(서비스) 생성
    WeatherAPI_Interface weatherAPIInterface = retrofit.create(WeatherAPI_Interface.class);


    // getService()
    public WeatherAPI_Interface getWeatherAPIInterface(){
        return weatherAPIInterface;
    }
}