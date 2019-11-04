package com.example.kwons.music_in_you.Weather.Interface;


import com.example.kwons.music_in_you.Weather.Model.Weather;

/**
 * Created by rohit on 10/15/15.
 */
public interface HttpCallback {

    public void onSuccess (Weather response);

    public void onFailure (String error);

}