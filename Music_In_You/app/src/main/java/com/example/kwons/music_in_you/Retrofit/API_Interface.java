package com.example.kwons.music_in_you.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface API_Interface {

    @Headers({"Content-Type: application/json"})

    //@Multipart
    //@FormUrlEncoded  //IllegalArgumentException: @Field parameters can only be used with form encoding 에러 해결하기 위해 사용
    @POST("rest-auth/registration/")
    Call<MemberResult> do_signUp(@Body MemberDTO memberDTO);


}
