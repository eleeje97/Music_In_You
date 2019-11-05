package com.example.kwons.music_in_you;

import android.app.Activity;
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

public class Weather_result_Test extends Activity {


    // 리스트
    public static ArrayList<MusicDTO> list; // 모든 노래 리스트
    public ArrayList<MusicDTO> musicList; // 특정 재생목록의 노래 리스트


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);


        Button retry_btn, play_btn;
        retry_btn = findViewById(R.id.retry);
        play_btn = findViewById(R.id.direct_play);
        ImageView album = findViewById(R.id.imageView3); // 노래 앨범 담을 이미지뷰

        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기

        DBOpenHelper mDbOpenHelper = new DBOpenHelper(getApplicationContext());
        mDbOpenHelper.open();

        Cursor iCursor;
        Bitmap bitmap; // 앨범 사진 가져올 비트맵
        BitmapDrawable bitmapDrawable; // 비트맵을 Drawable로 바꿔줄 변수


        iCursor = mDbOpenHelper.selectSongsByEmotion("happy");
        while (iCursor.moveToNext()) {
            int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
            String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));

            musicList.add(list.get(idx));
        }




        // 다시 추천 하기 버튼을 눌렀을 때
        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // 즉시 재생 버튼을 눌렀을 때
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





        // 이미지 뷰
        bitmap = BitmapFactory.decodeFile(MusicPlayActivity.getCoverArtPath(
                Long.parseLong(list.get(0).getAlbumId()),getApplicationContext()));
        if(bitmap != null) {
            bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
          //  all_songs.setBackground(bitmapDrawable);
          //  all_songs_count.setText(list.size() + all_songs_count.getText().toString());
        }
        else{
           // all_songs.setBackground(getActivity().getDrawable(R.drawable.default_music_album));
           // all_songs_count.setText(list.size() + all_songs_count.getText().toString());
        }




    }
}
