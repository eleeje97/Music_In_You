package com.example.kwons.music_in_you.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API_Interface {

    @Headers({"Content-Type: application/json"})

    @FormUrlEncoded  //IllegalArgumentException: @Field parameters can only be used with form encoding 에러 해결하기 위해 사용
    @POST("rest-auth/registration")
    Call<MemberDTO> do_signUp(@Field("email") String email, @Field("name") String name, @Field("password1") String password1,
                              @Field("password2") String password2, @Field("date_of_birth") String date_of_birth, @Field("music_preference") MusicPreference music_Preference);
    //@Field("music_preference") MusicPreference music_Preference


}
