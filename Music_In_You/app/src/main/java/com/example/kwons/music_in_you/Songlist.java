package com.example.kwons.music_in_you;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.kwons.music_in_you.Database.DBOpenHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class Songlist extends AppCompatActivity {
    MusicPlayActivity musicPlayActivity = (MusicPlayActivity)MusicPlayActivity.musicPlayActivity;
    static Songlist songlist;

    int playlist_position;

    private Button sort_btn; // 정렬 버튼
    private ListView listView ; // MP3 목록을 나타낼 리스트뷰
    public static ArrayList<MusicDTO> list; // 모든 노래 리스트
    public ArrayList<MusicDTO> musicList; // 특정 재생목록의 노래 리스트
    private SongAdapter adapter;

    public ArrayList<MusicDTO> playlist; // 목록버튼 통해 넘어올 때 보여줄 노래 리스트(인텐트를 통해 받음)

    final int ALL_SONGS = 0;
    final int LIKE_SONGS = 1;
    final int FREQUENT_SONGS = 2;
    final int MIYU_SONGS = 3;

    final int CURRENT_SONGS = 4; // 재생화면에서 목록버튼 눌러서 넘어올 때

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        songlist = Songlist.this;

        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기

        listView = findViewById(R.id.listview);
        sort_btn = findViewById(R.id.sort_btn);

        // playlist_position 값 가져오기
        Intent intent = getIntent();
        playlist_position = intent.getIntExtra("playlist_position",0);

        playlist = (ArrayList<MusicDTO>)intent.getSerializableExtra("currentList");


        // position에 따라 리스트 만들고 어댑터 연결
        musicList = getMusicList(playlist_position);
        adapter = new SongAdapter(this, musicList);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 현재 음악이 재생되고 있다면 종료함
                Intent intent = new Intent(getApplicationContext(), MusicPlayActivity.class); // 현재 선택된 곡을 재생
                if(musicPlayActivity != null){
                    musicPlayActivity.finish();
                } // 현재 실행되는 액티비티가 있다면 종료하고
                //musicPlayActivity.setPosition(position);
                intent.putExtra("playlist_position", position);
                intent.putExtra("playlist", musicList);

                startActivity(intent);
            }
        });


        // 제목순 정렬
        // 정렬버튼이 눌리면
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(list); // 정렬하도록 함
                listView.setAdapter(adapter);
                for(MusicDTO musicDTO : list) { System.out.println(musicDTO.getTitle()); }

            }
        });


    }


    ArrayList<MusicDTO> getMusicList(int position) {
        ArrayList<MusicDTO> musicList = new ArrayList<>();

        DBOpenHelper mDbOpenHelper = new DBOpenHelper(this);
        mDbOpenHelper.open();
        Cursor iCursor;

        switch (position) {
            case ALL_SONGS:
                musicList = list;
                break;

            case LIKE_SONGS:
                iCursor = mDbOpenHelper.selectLoveSongs();
                while (iCursor.moveToNext()) {
                    int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
                    musicList.add(list.get(idx));
                }
                iCursor.close();
                break;

            case FREQUENT_SONGS:
                iCursor = mDbOpenHelper.selectFrequentSongs();
                while (iCursor.moveToNext()) {
                    int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
                    musicList.add(list.get(idx));
                }
                iCursor.close();
                break;

            case MIYU_SONGS:
                // get preference
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String json = sharedPreferences.getString("MIYUList", null);
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

                musicList = urls;

                break;

            case CURRENT_SONGS:
                musicList = playlist;
                break;


        }


        mDbOpenHelper.close();

        return musicList;

    }


}