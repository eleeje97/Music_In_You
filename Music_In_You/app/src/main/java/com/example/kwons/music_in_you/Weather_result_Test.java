package com.example.kwons.music_in_you;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kwons.music_in_you.Database.DBOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class Weather_result_Test extends Activity {


    // 리스트
    public static ArrayList<MusicDTO> list; // 모든 노래 리스트
    public ArrayList<MusicDTO> musicList; // 특정 재생목록의 노래 리스트

    ImageView album; // 앨범 사진 보여줄 이미지뷰
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);


        Button retry_btn, play_btn;
        retry_btn = findViewById(R.id.retry);
        play_btn = findViewById(R.id.direct_play);
        album = findViewById(R.id.imageView3); // 노래 앨범 담을 이미지뷰

        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기
        musicList = new ArrayList<MusicDTO>();

        DBOpenHelper mDbOpenHelper = new DBOpenHelper(getApplicationContext());
        mDbOpenHelper.open();

        Cursor iCursor;



        // happy 감정의 음악 리스트 가져옴
        iCursor = mDbOpenHelper.selectSongsByEmotion("happy");
        while (iCursor.moveToNext()) {
            int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
            musicList.add(list.get(idx));
        }


        pickRandom();

        // 다시 추천 하기 버튼을 눌렀을 때
        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickRandom();
            }
        });


        // 즉시 재생 버튼을 눌렀을 때
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MusicPlayActivity.class);
                intent.putExtra("playlist_position", position);
                Log.i("포지션", position+"");
                Log.i("list포지션", list.get(position).getId()+"");
                // happy 목록 중에서 재생
                intent.putExtra("playlist", list);
                startActivity(intent);
            }
        });



    }

    void pickRandom() {
        Random random = new Random();
        int song_idx = random.nextInt(musicList.size());

        Bitmap bitmap; // 앨범 사진 가져올 비트맵
        BitmapDrawable bitmapDrawable; // 비트맵을 Drawable로 바꿔줄 변수

        bitmap = BitmapFactory.decodeFile(MusicPlayActivity.getCoverArtPath(
                Long.parseLong(list.get(song_idx).getAlbumId()),
                getApplicationContext()));
        bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        album.setBackground(bitmapDrawable);

        position = song_idx;
    }

}
