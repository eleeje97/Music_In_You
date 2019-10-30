package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Music_Classification extends AppCompatActivity{

    String name;
    TextView loading_comment;
    ImageView cat_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_classification);


        Intent intent = getIntent();
        name = intent.getExtras().getString("NAME");

        loading_comment = findViewById(R.id.loading_comment);
        loading_comment.setText("MIYU가 " + name + loading_comment.getText().toString());

        // 일단 고양이 사진 누르면 메인으로 넘어가도록
        cat_image = findViewById(R.id.imageView5);
        cat_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });

    }
}
