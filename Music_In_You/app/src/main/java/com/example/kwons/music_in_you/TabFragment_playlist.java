package com.example.kwons.music_in_you;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class TabFragment_playlist extends Fragment  {

    static TabFragment_playlist tabFragment_playlist;
    MusicPlayActivity musicPlayActivity = (MusicPlayActivity)MusicPlayActivity.musicPlayActivity;

    private Button sort_btn; // 정렬 버튼
    private ListView listView ; // MP3 목록을 나타낼 리스트뷰
    public static ArrayList<MusicDTO> list;
    private  MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        tabFragment_playlist = TabFragment_playlist.this;
        View view = inflater.inflate(R.layout.tab_playlist,container,false);

        list = MainActivity.mainActivity.getMusicList();

        listView = view.findViewById(R.id.listview);
        sort_btn = view.findViewById(R.id.sort_btn);
        adapter = new MyAdapter(getActivity(), list);



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // 현재 음악이 재생되고 있다면 종료함
                Intent intent = new Intent(getContext(), MusicPlayActivity.class); // 현재 선택된 곡을 재생
                if(musicPlayActivity != null){

                    musicPlayActivity.finish();

                } // 현재 실행되는 액티비티가 있다면 종료하고

                intent.putExtra("position", position);
                intent.putExtra("playlist", list);


                startActivity(intent);
            }
        });


        // 정렬버튼이 눌리면
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(list); // 정렬하도록 함
                listView.setAdapter(adapter);
                for(MusicDTO musicDTO : list) { System.out.println(musicDTO.getTitle()); }


            }
        });

        return view;
    }





}



