package com.example.kwons.music_in_you.Weather;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kwons.music_in_you.R;
import com.example.kwons.music_in_you.Weather.Model.WeatherData;
import com.example.kwons.music_in_you.Weather.Service.APIManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH";

    //private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;

    private Typeface weatherFont;
    private String place_location="";
    private ProgressBar spinner;

    TextView weather_report,place,weather_icon,country,icon_text;
    List myList ;
    String API_KEY="3075f51dd9331d6494bfc853c660dd65"; //insert api key here
    private final static String PATH_TO_WEATHER_FONT = "fonts/weather.ttf";
    private ListView lv;






    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scrolling);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        weather_icon=(TextView)findViewById(R.id.weather_icon);

        country=(TextView)findViewById(R.id.country);
        weatherFont = Typeface.createFromAsset(getAssets(), PATH_TO_WEATHER_FONT);
        weather_icon.setTypeface(weatherFont);

        weather_report = (TextView) findViewById(R.id.weather_report);
        place =(TextView)findViewById(R.id.place);

        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            String stringLongitude = String.valueOf(gpsTracker.longitude);

            Log.i("Weather_lat",stringLatitude);
            Log.i("Weather_lon",stringLongitude);

            APIManager.getApiService()
                    .getWeatherInfo(stringLatitude,
                    stringLongitude,
                    "10",
                    API_KEY
                    );


            String postalCode = gpsTracker.getPostalCode(this);

            String addressLine = gpsTracker.getAddressLine(this);

        }
        else
        {
            Log.e("No","Location");
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //gpsTracker.showSettingsAlert();
        }
    }




/*

    private Callback<WeatherData> callback = new Callback<WeatherData>() {

        public List<Weather> weathers;
        @Override
        public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
            //Log.w("code", String.valueOf(response.code()));

            weather_report.setText(response.body().getList().get(0).getWeather().get(0).getDescription());
            place.setText(response.body().getCity().getName());
            country.setText(response.body().getCity().getCountry());
            place_location =response.body().getCity().getName();


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

                Log.w("humidity",humidity[i]);
                Log.w("rainDescription",rain_description[i]);
                Log.w("icon",icon[i]);
                Log.w("time",time[i]);

                weathers.add(new Weather(String.valueOf(response.body().getList().get(i).getWeather().get(0).getIcon()), String.valueOf(response.body().getList().get(i).getMain().getHumidity()), String.valueOf(response.body().getList().get(i).getWeather().get(0).getDescription()), String.valueOf(response.body().getList().get(i).getDt())));

            }

            mAdapter = new WeatherAdapter(weathers,weatherFont);
            //mRecyclerView.setAdapter(mAdapter);
           // Log.w("Weather__Adapter","어댑터 생성");
            spinner.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(Call<WeatherData> call, Throwable t) {

            place.setText("Unable to retrieve data");

            spinner.setVisibility(View.GONE);


            Log.d("Weather__", "failure", t.getCause());
        }

    };


*/

}
