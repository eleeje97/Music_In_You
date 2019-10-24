package com.example.kwons.music_in_you;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpAPIClient {

    final static String signupURL = "13.125.21.218/rest-auth/login";

    // 회원 가입
    public String signUp(){

        // MemberInfo 객체 생성
        MemberInfo memberInfo = new MemberInfo();

        //try-catch
        try{
            // API사용하기 위해 HTTPURLConnection사용
            URL url = new URL(signupURL);

            // HTTP Connection 열고 호출
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            //InputStream
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            // inputStream -> String -> jsonObject로 변환
            JSONObject json = new JSONObject(getStringFromInputStream(inputStream));

        }catch (MalformedURLException e){
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;
        }catch (IOException e){
            System.err.println("URL Connection Failed");
            e.printStackTrace();
            return null;
        }catch(JSONException e){
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        }


        return "";
    }

    // inputStream을 String으로 변환해줄 함수
    private static String getStringFromInputStream(InputStream inputStream){

        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        String line;

        // try-catch
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

        } catch(IOException e){
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return stringBuilder.toString();

    }


}
