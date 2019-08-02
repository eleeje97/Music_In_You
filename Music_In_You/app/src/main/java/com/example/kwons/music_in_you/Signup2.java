package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Signup2 extends AppCompatActivity {

    // 컴포넌트 선언
    Button next_btn;
    TextView question_tv;
    RadioButton question_rb1;


    String name;
    String question;

    int page_flag = 0;
    String[] emotions = {"평소에", "기쁠 때", "슬플 때", "화날 때", "마음이 편안할 때"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        // 컴포넌트 가져오기
        next_btn = findViewById(R.id.next_btn);
        question_tv = findViewById(R.id.question_tv);
        question_rb1 = findViewById(R.id.question_rb1);


        Intent intent = getIntent();
        name = intent.getExtras().getString("NAME");
        question = question_tv.getText().toString();

        question_tv.setText(name + "님은 " + emotions[page_flag] + question);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_flag == 4) {
                    Intent intent = new Intent(getApplicationContext(), Music_Classification.class);
                    intent.putExtra("NAME", name);
                    startActivity(intent);
                } else {
                    onNextButtonClicked();
                }
            }
        });

    }

    void onNextButtonClicked() {
        page_flag++;
        question_tv.setText(name + "님은 " + emotions[page_flag] + question);

        if(page_flag == 4) {
            next_btn.setBackgroundResource(R.drawable.login_btn);
            next_btn.setText("DONE");
        }

        question_rb1.setChecked(true);

    }

}
