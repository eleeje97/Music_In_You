package com.example.kwons.music_in_you.Weather;

import android.util.Log;
import android.widget.TextView;

import com.example.kwons.music_in_you.R;
import com.example.kwons.music_in_you.Weather.Model.WeatherData;
import com.example.kwons.music_in_you.Weather.Service.APIManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenWeather {

    static String API_KEY="3075f51dd9331d6494bfc853c660dd65";
    static TextView weather_icon;


    public static void getWeather(String stringLatitude, String stringLongitude, TextView wi) {
        weather_icon = wi;
        APIManager.getApiService()
                .getWeatherInfo(stringLatitude,
                        stringLongitude,
                        "10",
                        API_KEY
                ).enqueue(callback);
    }


    private static Callback<WeatherData> callback = new Callback<WeatherData>() {

        public List<Weather> weathers;
        @Override
        public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {

            if(response.isSuccessful()){
                Log.w("retrofit",response.message());
            }


            Log.w("Weather__icon", response.body().getList().get(0).getWeather().get(0).getIcon());
            switch (response.body().getList().get(0).getWeather().get(0).getIcon()){
                case "01d":
                    weather_icon.setText(R.string.wi_day_sunny);

                    break;
                case "02d":
                    weather_icon.setText(R.string.wi_cloudy_gusts);

                    break;
                case "03d":
                    weather_icon.setText(R.string.wi_cloud_down);

                    break;
                case "04d":
                    weather_icon.setText(R.string.wi_cloudy);

                    break;
                case "04n":
                    weather_icon.setText(R.string.wi_night_cloudy);

                    break;
                case "10d":
                    weather_icon.setText(R.string.wi_day_rain_mix);
                    break;
                case "11d":
                    weather_icon.setText(R.string.wi_day_thunderstorm);
                    break;
                case "13d":
                    weather_icon.setText(R.string.wi_day_snow);
                    break;
                case "01n":
                    weather_icon.setText(R.string.wi_night_clear);
                    break;
                case "02n":
                    weather_icon.setText(R.string.wi_night_cloudy);
                    break;
                case "03n":
                    weather_icon.setText(R.string.wi_night_cloudy_gusts);
                    break;
                case "10n":
                    weather_icon.setText(R.string.wi_night_cloudy_gusts);
                    break;
                case "11n":
                    weather_icon.setText(R.string.wi_night_rain);
                    break;
                case "13n":
                    weather_icon.setText(R.string.wi_night_snow);
                    break;

            }
            String[]humidity = new String[10];
            String[]rain_description=new String[10];
            String[]icon=new String[10];
            String[]time=new String[10];
            weathers = new ArrayList<>();
            for (int i=0; i<response.body().getList().size();i++){
                humidity[i] = String.valueOf(response.body().getList().get(i).getMain().getHumidity());
                rain_description[i] = String.valueOf(response.body().getList().get(i).getWeather().get(0).getDescription());
                icon[i] = String.valueOf(response.body().getList().get(i).getWeather().get(0).getIcon());
                time[i] = String.valueOf(response.body().getList().get(i).getDt());

                weathers.add(new Weather(String.valueOf(response.body().getList().get(i).getWeather().get(0).getIcon()), String.valueOf(response.body().getList().get(i).getMain().getHumidity()), String.valueOf(response.body().getList().get(i).getWeather().get(0).getDescription()), String.valueOf(response.body().getList().get(i).getDt())));

            }

        }

        @Override
        public void onFailure(Call<WeatherData> call, Throwable t) {
            Log.d("Weather__", "failure", t.getCause());
        }

    };


}
