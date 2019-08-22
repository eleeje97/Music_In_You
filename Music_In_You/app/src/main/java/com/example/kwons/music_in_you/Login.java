package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    // 컴포넌트 선언
    EditText email_tf; // 이메일 텍스트필드
    TextView email_warn; // 이메일 경고메시지
    EditText password_tf; // 비밀번호 텍스트필드
    TextView password_warn; // 비밀번호 경고메시지
    Button signin_btn; // 로그인 버튼
    TextView signup_btn; // 회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 컴포넌트 가져오기
        email_tf = findViewById(R.id.email_tf);
        email_warn = findViewById(R.id.email_warn);
        password_tf = findViewById(R.id.password_tf);
        password_warn = findViewById(R.id.password_warn);
        signup_btn = findViewById(R.id.signup_btn);
        signin_btn = findViewById(R.id.signin_btn);


        // 이메일 형식 체크
        email_tf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else { // 이메일 textfield가 포커스를 잃으면
                    checkEmail();
                }

            }
        });

        // 비밀번호 공백 체크
        password_tf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else { // 비밀번호 textfield가 포커스를 잃으면
                    checkPassword();
                }

            }
        });


        // 로그인 버튼 리스너
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 이메일, 비밀번호 체크
                if(checkEmail() & checkPassword()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }


            }
        });


        // 회원가입 버튼 리스너
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup1.class);
                startActivityForResult(intent, 1000);
            }
        });
    }


    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail() {
        String email = email_tf.getText().toString();
        boolean valid = EMAIL_ADDRESS_PATTERN.matcher(email).matches();

        if(valid) {
            email_warn.setText("");
            return true;
        } else {
            email_warn.setText("이메일 형식으로 입력하세요");
            return false;
        }
    }


    private boolean checkPassword() {
        String password = password_tf.getText().toString();

        if(password.length() == 0) {
            password_warn.setText("비밀번호를 입력하세요");
            return false;
        } else {
            password_warn.setText("");
            return true;
        }
    }
}
