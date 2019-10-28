package com.example.kwons.music_in_you.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit 객체를 만들어줄 클래스
public class API_Client {

    // Base URL
    final static String baseUrl = "http://miyu-server-dev.ap-northeast-2.elasticbeanstalk.com/";
    // 싱글톤 패턴 사용
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

            // Retrofit 저장할 Retrofit 타입 변수 선언
            retrofit = new Retrofit.Builder()
                       .baseUrl(baseUrl) // 어떤 서버로 네트워크 통신을 요청할 것인지 설정
                       .addConverterFactory(GsonConverterFactory.create(gson)) // GsonConverter을 이용해서 데이터를 파싱
                       .build(); // Retrofit.Builder 객체에 설정한 정보를 이용해 실질적으로 Retrofit 객체를 만들어 반환

        }

        // retrofit 객체 반환
        return retrofit;
    }
}
