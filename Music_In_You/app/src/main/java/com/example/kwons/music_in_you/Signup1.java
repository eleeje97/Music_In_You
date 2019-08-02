package com.example.kwons.music_in_you;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Signup1 extends AppCompatActivity {

    // 컴포넌트 선언

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
                    if(checkEmail(email_et.getText().toString())) {
                        email_warn.setText("");
                    } else {
                        email_warn.setText("이메일 형식으로 입력하세요");
                    }
                }

            }
        });

        // 비밀번호 공백 체크
        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    if(password_et.getText().length() == 0) {
                        password_warn.setText("비밀번호를 입력하세요");
                    } else {
                        password_warn.setText("");
                    }
                }

            }
        });

        // 비밀번호 일치 체크
        password_check_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    if(!password_check_et.getText().toString().equals(password_et.getText().toString())) {
                        password_check_warn.setText("비밀번호가 일치하지 않습니다");
                    } else {
                        password_check_warn.setText("");
                    }
                }

            }
        });

        // 이름 공백 체크
        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    if(name_et.getText().length() == 0) {
                        name_warn.setText("이름을 입력하세요");
                    } else {
                        name_warn.setText("");
                    }
                }

            }
        });


        //final DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerStyle, onDateSetListener, 2019, 6, 31);


        // 생년월일 형식대로 입력
        birthday_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = birthday_et.getText().toString();
                if(text.length()==4) {
                    birthday_et.setText(text + ".");
                    birthday_et.setSelection(birthday_et.length());
                } else if(text.length()==7) {
                    birthday_et.setText(text + ".");
                    birthday_et.setSelection(birthday_et.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 생년월일 공백 체크
        birthday_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    //datePickerDialog.show();

                } else {
                    if(birthday_et.getText().length() == 0) {
                        birthday_warn.setText("생년월일을 입력하세요");
                    } else {
                        birthday_warn.setText("");
                    }
                }

            }
        });





        // 다음 버튼 리스너
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup2.class);
                intent.putExtra("NAME", name_et.getText().toString());
                startActivity(intent);
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

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthday_et.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
            Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
        }
    };

}
