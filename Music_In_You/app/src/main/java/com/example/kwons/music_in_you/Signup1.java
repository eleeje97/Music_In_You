package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwons.music_in_you.Retrofit.API_Client;
import com.example.kwons.music_in_you.Retrofit.API_Interface;
import com.example.kwons.music_in_you.Retrofit.MemberDTO;
import com.example.kwons.music_in_you.Retrofit.MusicPreference;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup1 extends AppCompatActivity {

    /** 컴포넌트 선언 **/

    // 텍스트필드
    EditText email_et;
    EditText password_et;
    EditText password_check_et;
    EditText name_et;
    EditText birthday_et;

    // 경고메시지
    TextView email_warn;
    TextView password_warn;
    TextView password_check_warn;
    TextView name_warn;
    TextView birthday_warn;


    // DatePickerDialog
    DatePickerDialog datePickerDialog;

    Button next_btn; // 다음 버튼


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        // 컴포넌트 가져오기
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        password_check_et = findViewById(R.id.password_check_et);
        name_et = findViewById(R.id.name_et);
        birthday_et = findViewById(R.id.birthday_et);

        email_warn = findViewById(R.id.email_warn);
        password_warn = findViewById(R.id.password_warn);
        password_check_warn = findViewById(R.id.password_check_warn);
        name_warn = findViewById(R.id.name_warn);
        birthday_warn = findViewById(R.id.birthday_warn);

        next_btn = findViewById(R.id.next_btn);



        // 이메일 형식 체크
        email_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else { // 이메일 textfield가 포커스를 잃으면
                    checkEmail();
                }

            }
        });

        // 비밀번호 공백 체크
        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    checkPassword();
                }

            }
        });

        // 비밀번호 일치 체크
        password_check_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    checkPassword_check();
                }

            }
        });

        // 이름 공백 체크
        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    checkName();
                }

            }
        });


        // DatePickerDialog 생성, 리스너 설정
        datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setDialogListener(new DatePickerDialog.DatePickerDialogListener() {
            @Override
            public void onPositiveClicked(int year, int month, int day) {
                setDate(year, month, day);
            }

        });

        // DatePickerDialog 띄우기
        birthday_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });



        // 다음 버튼 리스너
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모든 항목이 알맞게 입력되었으면 다음 페이지로 넘어가도록
                if(checkEmail() & checkPassword() & checkPassword_check() & checkName() & checkBirthday()) {

                    // 이메일 중복 확인
                    API_Interface apiservice = API_Client.getClient().create(API_Interface.class);

                    //MemberDTO 객체를 생성
                    MemberDTO memberDTO = new MemberDTO(email_et.getText().toString(),
                                                        name_et.getText().toString(),
                                                        password_et.getText().toString(),
                                                        password_check_et.getText().toString(),
                                                        birthday_et.getText().toString(),
                                                        null);

                    System.out.println("이메일" + memberDTO.getEmail());
                    System.out.println("음악취향" + memberDTO.getMusic_prefernce());

                    Call<MemberDTO> call = apiservice.do_signUp(memberDTO.getEmail(),
                            memberDTO.getName(),
                            memberDTO.getPassword1(),
                            memberDTO.getPassword2(),
                            memberDTO.getDate_of_birth(),
                            null);

                    call.enqueue(new Callback<MemberDTO>() {
                        @Override
                        public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {

                            //확인
                            Log.i("Rest통신 성공",response.message());

                            // 응답받은 코드 번호가 400 이라면
                            if(response.code() == 500){
                                Log.i("Rest_상태코드 : " ,"500");
                            }
                            else {
                                Log.i("Rest_상태코드 다른것","");
                            }

                            Intent intent = new Intent(getApplicationContext(), Signup2.class);
                            intent.putExtra("NAME", name_et.getText().toString());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<MemberDTO> call, Throwable t) {
                            // Log error here since request failed
                            //Log.e("tag", t.toString());
                            Log.i("Rest통신 실패:" ,t.toString());


                        }
                        });


                }

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
        String email = email_et.getText().toString();
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
        String password = password_et.getText().toString();

        if(password.length() == 0) {
            password_warn.setText("비밀번호를 입력하세요");
            return false;
        } else {
            password_warn.setText("");
            return true;
        }
    }

    private boolean checkPassword_check() {
        String password_check = password_check_et.getText().toString();

        if (!password_check.equals(password_et.getText().toString())) {
            password_check_warn.setText("비밀번호가 일치하지 않습니다");
            return false;
        } else {
            password_check_warn.setText("");
            return true;
        }
    }

    private boolean checkName() {
        String name = name_et.getText().toString();

        if(name.length() == 0) {
            name_warn.setText("이름을 입력하세요");
            return false;
        } else {
            name_warn.setText("");
            return true;
        }
    }

    private boolean checkBirthday() {
        String birthday = birthday_et.getText().toString();

        if(birthday.length() == 0) {
            birthday_warn.setText("생년월일을 입력하세요");
            return false;
        } else {
            birthday_warn.setText("");
            return true;
        }
    }


    private void setDate(int year, int month, int day) {
        birthday_et.setText(year + "." + (month + 1) + "." + day);
    }



}
