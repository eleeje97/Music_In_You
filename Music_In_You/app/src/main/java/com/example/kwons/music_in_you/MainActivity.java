package com.example.kwons.music_in_you;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static MainActivity mainActivity;
    private Button search; // 검색을 위한 버튼
    LinearLayout musicplayer;


    // miniplayer 변수 선언
    private ImageView album,previous,next;
    private Button play,pause;

    ArrayList<MusicDTO> list;

    PageAdapter pageAdapter;

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

        // miniplayer 변수 생성
        /*
        album = findViewById(R.id.mini_album);
        previous = findViewById(R.id.mini_pre);
        next = findViewById(R.id.mini_next);
        play = findViewById(R.id.mini_play);
        pause = findViewById(R.id.mini_pause);
       */
        // 검색 버튼
        search = findViewById(R.id.search_btn);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serarch_intent = new Intent(MainActivity.this, Search_MusicActivity.class);
                startActivity(serarch_intent);
                Toast.makeText(getApplicationContext(), "search버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });


        /*musicplayer = findViewById(R.id.musicplayer);
        View.OnClickListener musicplayerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 재생중인 곡이 있다면 해당 곡의 MusicPlayActivity로 이동
                if (MusicPlayActivity.mediaPlayer != null) {
                    Intent intent = new Intent(MainActivity.this, MusicPlayActivity.class);
                    intent.putExtra("playlist_position", 1); // 재생되는 곡의 포지션을 가지고 전달
                    startActivity(intent);
                } else { // 재생중인 음악이 없다면 토스트 메시지로 알림
                    Toast.makeText(getApplicationContext(), "재생중인 음악이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        musicplayer.setOnClickListener(musicplayerListener);

        list = getMusicList(); // 사용자 디바이스 안에 있는 음악파일 리스트를 가져와 리스트를 만든다.


        /* 임시로 음악 DB 여기로 생성 */
        createMusicDB();

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

        /* DB 조회 */
        DBOpenHelper mDbOpenHelper = new DBOpenHelper(this);
        mDbOpenHelper.open();

        Cursor iCursor = mDbOpenHelper.selectColumns();
        while(iCursor.moveToNext()){
            int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
            String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));
            String happy = iCursor.getString(iCursor.getColumnIndex("happy"));
            String sad = iCursor.getString(iCursor.getColumnIndex("sad"));
            String aggressive = iCursor.getString(iCursor.getColumnIndex("aggressive"));
            String relaxed = iCursor.getString(iCursor.getColumnIndex("relaxed"));
            int love = iCursor.getInt(iCursor.getColumnIndex("love"));
            int count = iCursor.getInt(iCursor.getColumnIndex("count"));

            String Result = idx + "," +song_id + "," + happy + "," + sad + "," + aggressive + "," + relaxed + "," + love + "," + count;
            Log.e("DB조회", Result);
        }
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
                MediaStore.Audio.Media.DATA // 실제 데이터 위치
        };

        // Cursor을 이용해서 mp3데이터를 가져옴
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection, null, null, null); // 타이틀 순으로 정렬 MediaStore.Audio.Media.TITLE+"ASC"


        Log.e("경로: ",MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() );
        while (cursor.moveToNext()){ // 가져올 음악 데이터가 존재한다면 계속 true
            MusicDTO musicDTO = new MusicDTO();

            musicDTO.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDTO.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDTO.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDTO.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicDTO.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicDTO.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            //Log.e("리스트 목록", musicDTO.getTitle());
            list.add(musicDTO);
        }
        cursor.close();

        return list;
    }


    void createMusicDB() {
        DBOpenHelper mDbOpenHelper = new DBOpenHelper(this);
        mDbOpenHelper.open();

        //mDbOpenHelper.deleteTable();
        mDbOpenHelper.create(); // 테이블 생성

        int i = 0;
        for (MusicDTO musicDTO : list) {
            mDbOpenHelper.insertColumn(i, musicDTO.getId(), 0.7, 0.2, 0.01, 0.05, 0, 0);
            i++;
        }

    }

}
