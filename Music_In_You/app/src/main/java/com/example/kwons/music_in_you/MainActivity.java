package com.example.kwons.music_in_you;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LinearLayout playlist;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        playlist = findViewById(R.id.playlist);





        TabLayout tabLayout = findViewById(R.id.tabs) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                changeView(pos) ;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        }) ;

    }

    private void changeView(int index) {
        TextView textView1 = (TextView) findViewById(R.id.home) ;
        // playlist
        TextView textView3 = (TextView) findViewById(R.id.setting) ;

        switch (index) {
            case 0 :
                textView1.setVisibility(View.VISIBLE) ;
                playlist.setVisibility(View.GONE) ;
                textView3.setVisibility(View.GONE) ;
                break ;
            case 1 :
                textView1.setVisibility(View.GONE) ;
                playlist.setVisibility(View.VISIBLE) ;
                textView3.setVisibility(View.GONE) ;
                break ;
            case 2 :
                textView1.setVisibility(View.GONE) ;
                playlist.setVisibility(View.GONE) ;
                textView3.setVisibility(View.VISIBLE) ;
                break ;

        }
    }


}
