package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Music_Classification extends AppCompatActivity{

    String name;
    TextView loading_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_classification);


        Intent intent = getIntent();
        name = intent.getExtras().getString("NAME");

        loading_comment = findViewById(R.id.loading_comment);
        loading_comment.setText("MIYU가 " + name + loading_comment.getText().toString());

    }
}
