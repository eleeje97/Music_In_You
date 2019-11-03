package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwons.music_in_you.Retrofit.API_Client;
import com.example.kwons.music_in_you.Retrofit.API_Interface;
import com.example.kwons.music_in_you.Retrofit.MemberDTO;
import com.example.kwons.music_in_you.Retrofit.MusicPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup2 extends AppCompatActivity {

    // 컴포넌트 선언
    Button prev_btn;
    Button next_btn;
    TextView question_tv;

    RadioGroup radioGroup;
    RadioButton question_rb1;


    String name;
    String question;

    int page_flag = 0;
    String[] emotions = {"평소에", "기쁠 때", "슬플 때", "화날 때", "마음이 편안할 때"};
    String[] preference = {"dance","tearful","destructive","sentimental"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        // 컴포넌트 가져오기
        prev_btn = findViewById(R.id.prev_btn);
        next_btn = findViewById(R.id.next_btn);
        question_tv = findViewById(R.id.question_tv);

        radioGroup = findViewById(R.id.radioGroup);
        question_rb1 = findViewById(R.id.question_rb1);


        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        question = question_tv.getText().toString();

        question_tv.setText((page_flag+1)+". "+ name + "님은 " + emotions[page_flag] + question);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_flag == 4) {
                    //Intent intent = new Intent(getApplicationContext(), Music_Classification.class);
                    //intent.putExtra("name", name);
                    //startActivity(intent);
                } else {
                    onNextButtonClicked();
                }
            }
        });

        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_flag == 0) {
                    finish();
                } else {
                    onPrevButtonClicked();
                }
            }
        });

    }

    void onNextButtonClicked() {

        page_flag++;
        question_tv.setText((page_flag+1)+". "+ name + "님은 " + emotions[page_flag] + question);

        if(page_flag == 4) {
            next_btn.setBackgroundResource(R.drawable.login_btn);
            next_btn.setText("DONE");


            Intent intent = getIntent();
            String email = intent.getExtras().getString("email");
            Log.i("email", email);
            String name = intent.getExtras().getString("name");
            String password1 = intent.getExtras().getString("password1");
            String password2 = intent.getExtras().getString("password2");
            String birth = intent.getExtras().getString("birth");
            Log.i("Retrofit 통신에 보낼 값", email +"," + name);

            // Music_Preference
            MusicPreference musicPreference = new MusicPreference("danceful","tearful","danceful","tearful","tearful");
            Log.i("Retrofit", "music_preference객체 생성");

            // Call객체 생성
            Call<MemberDTO> call = API_Client.getApi_client_instance()
                                   .getApi_service()
                                   .do_signUp(email,name,password1,password2,birth,musicPreference);

            // enqueue()
            call.enqueue(new Callback<MemberDTO>() {
                @Override
                public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {
                    Log.d("Retrofit", response.toString());
                    Log.d("Retrofit", String.valueOf(response.code()));

                    if(response.body() != null){
                        System.out.println("Retrofit json 결과:" + response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<MemberDTO> call, Throwable t) {
                    Log.e("Retrofit ERROR", t.getMessage());
                    Log.e("Retrofit ERROR",t.getCause().toString());
                }
            });





        }

        // API 호출
        else if(page_flag == 5){


        }

        radioGroup.check(R.id.question_rb1);

    }

    void onPrevButtonClicked() {
        page_flag--;
        question_tv.setText((page_flag+1)+". "+ name + "님은 " + emotions[page_flag] + question);

        if(page_flag < 4) {
            next_btn.setBackgroundResource(R.drawable.signup_next_btn);
        }

        radioGroup.check(R.id.question_rb1);

    }


}
