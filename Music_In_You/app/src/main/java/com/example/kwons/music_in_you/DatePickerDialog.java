package com.example.kwons.music_in_you;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerDialog extends Dialog implements View.OnClickListener {

    DatePicker datePicker;

    private TextView positiveButton;
    private TextView negativeButton;

    DatePickerDialogListener datePickerDialogListener;

    // 생성자
    public DatePickerDialog(@NonNull Context context) {
        super(context);
    }

    public void setDialogListener(DatePickerDialogListener datePickerDialogListener){
        this.datePickerDialogListener = datePickerDialogListener;
    }

    interface DatePickerDialogListener {
        void onPositiveClicked(int year, int month, int day);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker_dialog);

        datePicker = findViewById(R.id.datePicker);

        positiveButton = findViewById(R.id.pButton);
        negativeButton = findViewById(R.id.nButton);

        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pButton: //확인 버튼을 눌렀을 때
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                datePickerDialogListener.onPositiveClicked(year, month, day);
                dismiss();
                break;
            case R.id.nButton: //취소 버튼을 눌렀을 때
                cancel();
                break;
        }

    }

}
