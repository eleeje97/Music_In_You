package com.example.kwons.music_in_you;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kwons.music_in_you.Database.DBOpenHelper;
import com.example.kwons.music_in_you.Service.MusicService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static MainActivity mainActivity;
    private Button search; // 검색을 위한 버튼
    LinearLayout musicplayer;

    // miniplayer 변수 선언
    private ImageView album,previous,next;
    private Button play,pause;

    ArrayList<MusicDTO> list;

    PageAdapter pageAdapter;

    // 플로팅 버튼
    private FloatingActionButton fab_main, fab_voice_detector, fab_playmusic;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = MainActivity.this;


        // 세개의 탭을 만든다
        final TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("PLAYLIST"));
        tabLayout.addTab(tabLayout.newTab().setText("SETTING"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // 플로팅 액션 버튼
        fab_main = findViewById(R.id.fab_main);
        fab_playmusic = findViewById(R.id.fab_music_play);
        fab_voice_detector = findViewById(R.id.fab_voice_detector);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        // 플로팅 버튼 리스너 추가
        fab_main.setOnClickListener(this);
        fab_playmusic.setOnClickListener(this);
        fab_voice_detector.setOnClickListener(this);
        // 검색 버튼
        search = findViewById(R.id.search_btn);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serarch_intent = new Intent(MainActivity.this, Search_MusicActivity.class);
                startActivity(serarch_intent);
               //Toast.makeText(getApplicationContext(), "search버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });


        list = getMusicList(); // 사용자 디바이스 안에 있는 음악파일 리스트를 가져와 리스트를 만든다.


        // 각 탭의 내용을 보여주는 view pager
        final ViewPager viewPager = findViewById(R.id.pager);


        // 탭과 프레그먼트를 연결해주는 PageAdapter
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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

        Intent intent = getIntent();

        int position = intent.getIntExtra("playlist_position", 0);
        viewPager.setCurrentItem(position);



    }


    /*사용자 디바이스에서 음악파일 가져와 리스트 만드는 함수*/
    public ArrayList<MusicDTO> getMusicList(){
        ArrayList<MusicDTO> list = new ArrayList<>(); //

        // 가져올 음악에 대한 정보를 받아옴
        String[] projection = {
                MediaStore.Audio.Media._ID, // 가져올 음악의 컬럼
                MediaStore.Audio.Media.ALBUM_ID, // 가져올 음악의 앨범 이름
                MediaStore.Audio.Media.TITLE, // 가져올 음악의 제목
                MediaStore.Audio.Media.ARTIST, // 가져올 음악의 아티스트
                MediaStore.Audio.Media.DURATION, // 음악시간
                MediaStore.Audio.Media.DATA, // 실제 데이터 위치
                MediaStore.Audio.Media.DATE_ADDED // 음악이 추가된 날짜
        };

        // Cursor을 이용해서 mp3데이터를 가져옴
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection, null, null, null); // 타이틀 순으로 정렬 MediaStore.Audio.Media.TITLE+"ASC"


        //Log.e("경로: ",MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString());
        while (cursor.moveToNext()){ // 가져올 음악 데이터가 존재한다면 계속 true
            MusicDTO musicDTO = new MusicDTO();

            musicDTO.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDTO.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDTO.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDTO.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicDTO.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicDTO.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            musicDTO.setDate_added(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)));
            //Log.e("음악 목록", musicDTO.toString());
            list.add(musicDTO);
        }
        cursor.close();

        return list;

    }



    // 플로팅 버튼 클릭 이벤트
    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.fab_main:
                animation();
                break;

            case R.id.fab_music_play:
                animation();


                // 현재 재생중인 곡이 있다면 해당 곡의 MusicPlayActivity로 이동
                if (MusicService.mediaPlayer.isPlaying()) {

                    // get preference
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String json = sharedPreferences.getString("MusicList", null);
                    ArrayList<MusicDTO> urls = new ArrayList<MusicDTO>();
                    Gson gson = new Gson();
                    if (json != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(json);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String url = jsonArray.optString(i);
                                MusicDTO musicDTO = gson. fromJson(url, MusicDTO.class);
                                urls.add(musicDTO);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    int position = sharedPreferences.getInt("MusicPosition", 0);

                    Intent intent = new Intent(getApplicationContext(), MusicPlayActivity.class);
                    intent.putExtra("playlist", urls);
                    intent.putExtra("playlist_position",position);
                    intent.putExtra("floatingButton", true);
                    startActivity(intent);

                } else { // 재생중인 음악이 없다면 토스트 메시지로 알림
                    Toast.makeText(getApplicationContext(), "재생중인 음악이 없습니다.", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.fab_voice_detector:
                animation();
                Intent intent2 = new Intent(getApplicationContext(), VoiceDetection.class);
                startActivity(intent2);
                break;

        }
    }

    // 플로팅 버튼 애니메이션 메서드
    public void animation(){


        if(isFabOpen){
            fab_playmusic.startAnimation(fab_close);
            fab_voice_detector.startAnimation(fab_close);
            fab_playmusic.setClickable(false);
            fab_voice_detector.setClickable(false);
            isFabOpen = false;
        }

        else{
            fab_playmusic.startAnimation(fab_open);
            fab_voice_detector.startAnimation(fab_open);
            fab_playmusic.setClickable(true);
            fab_voice_detector.setClickable(true);
            isFabOpen = true;

        }

    }
}
