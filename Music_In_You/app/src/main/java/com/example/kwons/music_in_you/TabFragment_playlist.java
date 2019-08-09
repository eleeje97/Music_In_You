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

public class TabFragment_playlist extends Fragment  {

    //MusicPlayActivity musicPlayActivity_activity = (MusicPlayActivity)MusicPlayActivity.musicPlayActivity;
    private Button search; // 검색을 위한 버튼
    private ListView listView ; // MP3 목록을 나타낼 리스트뷰
    public static ArrayList<MusicDTO> list ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(R.layout.tab_playlist,container,false);

        listView = view.findViewById(R.id.listview);
        search = view.findViewById(R.id.search_btn);


        //MyAdapter adapter = new MyAdapter(getActivity(),list);


        return view;
    }
}



