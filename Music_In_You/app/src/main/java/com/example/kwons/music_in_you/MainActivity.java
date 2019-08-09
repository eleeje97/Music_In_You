package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private Button search; // 검색을 위한 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 세개의 탭을 만든다
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("PLAYLIST"));
        tabLayout.addTab(tabLayout.newTab().setText("SETTING"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // 검색 버튼
        search = (Button)findViewById(R.id.search_btn);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serarch_intent = new Intent(MainActivity.this,Search_MusicActivity.class);
                startActivity(serarch_intent);
                Toast.makeText(getApplicationContext(),"search버튼 눌림",Toast.LENGTH_SHORT).show();
            }
        });

        // 각 탭의 내용을 보여주 view pager
        final ViewPager viewPager = findViewById(R.id.pager);



        // 탭과 프레그먼트를 연결해주는 PageAdapter
        final PagerAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
