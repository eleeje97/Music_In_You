package com.example.kwons.music_in_you;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kwons.music_in_you.Database.DBOpenHelper;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Random;

public class MusicRecommendation extends AppCompatActivity {
    TextView recommend_text; // 추천멘트
    LinearLayout recommend_playlist; // 추천목록
    TextView playlist_title; // 추천목록 title
    TextView playlist_count; // 추천곡 수

    String emotion; // 음성분석 결과감정

    ArrayList<MusicDTO> all_list; // 모든 노래 리스트
    ArrayList<MusicDTO> list; // 감정별 노래 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_recommendation);

        recommend_text = findViewById(R.id.recommend_text);
        recommend_playlist = findViewById(R.id.recommend_playlist);
        playlist_title = findViewById(R.id.playlist_title);
        playlist_count = findViewById(R.id.playlist_count);

        Intent emotionIntent = getIntent();
        emotion = emotionIntent.getStringExtra("EmotionResult");

        all_list = MainActivity.mainActivity.getMusicList();

        list = getList(emotion);

        setPreference();


        recommend_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Songlist.class);
                intent.putExtra("playlist_position", 3);
                intent.putExtra("currentList", list);
                startActivity(intent);
            }
        });


    }

    ArrayList<MusicDTO> getList(String emotion) {
        ArrayList<MusicDTO> list = new ArrayList<>();


        DBOpenHelper mDbOpenHelper = new DBOpenHelper(getApplicationContext());
        mDbOpenHelper.open();

        Cursor iCursor;
        Bitmap bitmap; // 앨범 사진 가져올 비트맵
        BitmapDrawable bitmapDrawable; // 비트맵을 Drawable로 바꿔줄 변수

        switch(emotion) {
            case "happy":
                iCursor = mDbOpenHelper.selectSongsByEmotion("happy");
                Log.e("EMOTION 조회", "Happy 곡 개수 - " + iCursor.getCount() + "");
                while (iCursor.moveToNext()) {
                    int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
                    String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));

                    String Result = "idx = " + idx + ", song_id = " + song_id;
                    Log.e("EMOTION 조회", Result);

                    list.add(all_list.get(idx));
                }

                recommend_text.setText("오늘 기분 좋은 일 있으신가요?\nMIYU가 신나는 노래를 들려드릴게요!");
                playlist_title.setText("Happy");
                playlist_count.setText(list.size() + "곡");

                break;

            case "sad":
                iCursor = mDbOpenHelper.selectSongsByEmotion("sad");
                Log.e("EMOTION 조회", "곡 개수 - " + iCursor.getCount() + "");
                while (iCursor.moveToNext()) {
                    int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
                    String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));

                    String Result = "idx = " + idx + ", song_id = " + song_id;
                    Log.e("EMOTION 조회", Result);

                    list.add(all_list.get(idx));
                }

                recommend_text.setText("지치고 힘든 일들에 기운이 없으신가요?\nMIYU가 당신의 지친 마음을 달래드릴게요.");
                playlist_title.setText("Sad");
                playlist_count.setText(list.size() + "곡");

                break;

            case "aggressive":
                iCursor = mDbOpenHelper.selectSongsByEmotion("aggressive");
                Log.e("EMOTION 조회", "곡 개수 - " + iCursor.getCount() + "");
                while (iCursor.moveToNext()) {
                    int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
                    String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));

                    String Result = "idx = " + idx + ", song_id = " + song_id;
                    Log.e("EMOTION 조회", Result);

                    list.add(all_list.get(idx));
                }

                recommend_text.setText("분노를 가라앉히는 가장 좋은 방법은\n아무것도 하지 않는 것이라고 해요!\nMIYU와 함께 힐링타임을 가져볼까요?");
                playlist_title.setText("Aggressive");
                playlist_count.setText(list.size() + "곡");

                break;

            case "relaxed":
                iCursor = mDbOpenHelper.selectSongsByEmotion("relaxed");
                Log.e("EMOTION 조회", "곡 개수 - " + iCursor.getCount() + "");
                while (iCursor.moveToNext()) {
                    int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
                    String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));

                    String Result = "idx = " + idx + ", song_id = " + song_id;
                    Log.e("EMOTION 조회", Result);

                    list.add(all_list.get(idx));
                }

                recommend_text.setText("지금 이 감정을 노래와 함께\n두배로 즐겨보는 건 어떠세요?");
                playlist_title.setText("Relaxed");
                playlist_count.setText(list.size() + "곡");

                break;

            case "normal":

                Random random = new Random();
                for (int i = 0; i < 20; i++) {
                    int position = random.nextInt(all_list.size());
                    list.add(all_list.get(position));
                }


                recommend_text.setText("평소 즐겨듣던 노래를\n추천해드릴게요!");
                playlist_title.setText("Normal");
                playlist_count.setText(list.size() + "곡");

                break;

        }

        if(list.size() != 0) {
            bitmap = BitmapFactory.decodeFile(MusicPlayActivity.getCoverArtPath(
                    Long.parseLong(list.get(0).getAlbumId()),
                    getApplicationContext()));
            if (bitmap != null) {
                bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                recommend_playlist.setBackground(bitmapDrawable);
            } else {
                recommend_playlist.setBackground(getDrawable(R.drawable.default_music_album));
            }

        }

        return list;
    }


    public void setPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();

        // 현재 재생 중인 음악 리스트
        Gson gson = new Gson();
        for (int i = 0; i < list.size(); i++) {
            String music_json = gson.toJson(list.get(i));
            jsonArray.put(music_json);
        }
        if (!list.isEmpty()) {
            editor.putString("MIYUList", jsonArray.toString());
        } else {
            editor.putString("MIYUList", null);
        }

        editor.apply();
    }


}
