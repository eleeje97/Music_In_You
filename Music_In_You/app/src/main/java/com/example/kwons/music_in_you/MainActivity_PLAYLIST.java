package com.example.kwons.music_in_you;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_PLAYLIST extends AppCompatActivity {

    static MainActivity_PLAYLIST mainActivity_playlist;
    MusicPlayActivity musicPlayActivity_activity = (MusicPlayActivity)MusicPlayActivity.musicPlayActivity;
    private Button search; // 검색을 위한 버튼
    private ListView listView ; // MP3 목록을 나타낼 리스트뷰
    public static ArrayList<MusicDTO> list ;
    //private Intent state_intent;
    //Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_playlist);
        mainActivity_playlist = MainActivity_PLAYLIST.this;

        getMusicList(); // 사용자 디바이스 안에 있는 음악파일 리스트를 가져와 리스트를 만듦
        listView = (ListView)findViewById(R.id.listview);
        search = (Button)findViewById(R.id.search_btn);

        //state_intent = getIntent();
        //final Boolean random_state = state_intent.getExtras().getBoolean("isRandomed");

        MyAdapter adapter = new MyAdapter(this,list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // 현재 음악이 재생되고 있다면 종료함
                Intent intent = new Intent(MainActivity_PLAYLIST.this,MusicPlayActivity.class); // 현재 선택된 곡을 재생
                if(MusicPlayActivity.musicPlayActivity != null){

                    musicPlayActivity_activity.finish();} // 현재 실행되는 액티비티가 있다면 종료하고

                intent.putExtra("position",position);
                intent.putExtra("playlist",list);


                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serarch_intent = new Intent(MainActivity_PLAYLIST.this,Search_MusicActivity.class);
                startActivity(serarch_intent);
                Toast.makeText(getApplicationContext(),"search버튼 눌림",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static ArrayList<MusicDTO> getList() {
        return list;
    }


    /*사용자 디바이스에서 음악파일 가져와 리스트 만드는 함수*/

    public void getMusicList(){
        list = new ArrayList<>(); //

        // 가져올 음악에 대한 정보를 받아옴
        String[] projection = {MediaStore.Audio.Media._ID, // 가져올 음악의 컬럼
                                MediaStore.Audio.Media.ALBUM_ID, // 가져올 음악의 앨범 이름
                                MediaStore.Audio.Media.TITLE, // 가져올 음악의 제목
                                MediaStore.Audio.Media.ARTIST, // 가져올 음악의 아티스트
                                MediaStore.Audio.Media.DURATION, // 음악시간
                                MediaStore.Audio.Media.DATA // 실제 데이터 위치

        };
        // Cursor을 이용해서 mp3데이터를 가져옴

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection, null, null, null); // 타이틀 순으로 정렬 MediaStore.Audio.Media.TITLE+"ASC"
            //.EXTERNAL_CONTENT_URI


        Log.e("경로: ",MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() );
        while (cursor.moveToNext()){ // 가져올 음악 데이터가 존재한다면 계속 true
            MusicDTO musicDTO = new MusicDTO();

            musicDTO.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDTO.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDTO.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDTO.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicDTO.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicDTO.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            Log.e("리스트 목록", musicDTO.getTitle());
            list.add(musicDTO);
        }
        cursor.close();

        }
    }
