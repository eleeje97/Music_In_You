package com.example.kwons.music_in_you.Retrofit;



import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kwons.music_in_you.R;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
    }

    Call<WeatherTest> call = WeatherAPI_Client.getWeatherAPIClient()
                            .getWeatherAPIInterface()
                            .getCurrentWeather("latitude" ,"longtitude",
                                        "3075f51dd9331d6494bfc853c660dd65");





}
