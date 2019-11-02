package com.example.kwons.music_in_you.Retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.HttpRetryException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RestError{


    public String error = "An error occured";



    public RestError(Throwable error){
       /* if (error instanceof SecurityException) {
            String errorJsonString = null;
            try {
                errorJsonString = ((SecurityException)
                        error)response().errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JsonElement parsedString = new
                    JsonParser().parse(errorJsonString);
            this.error = parsedString.getAsJsonObject()
                    .get("error")
                    .getAsString();
        } else {
            this.error = error.getMessage() != null ? error.getMessage() : this.error;
        }*/
    }
}
