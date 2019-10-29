package com.example.kwons.music_in_you;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TabFragment_playlist extends Fragment  {

    private ListView listView ; // 재생목록을 나타낼 리스트뷰
    ArrayList<PlaylistItem> playlist_list; // 재생목록을 담는 리스트

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.tab_playlist,container,false);

        listView = view.findViewById(R.id.listview); // 재생목록 리스트뷰를 가져옴
        playlist_list = new ArrayList<>(); // 재생목록을 담는 리스트

        PlaylistItem all_songs = new PlaylistItem("모든 노래");
        PlaylistItem recent_songs = new PlaylistItem("최근 추가된 곡");
        PlaylistItem like_songs = new PlaylistItem("좋아요 곡");
        PlaylistItem frequent_songs = new PlaylistItem("많이 재생한 곡");
        PlaylistItem miyu_songs = new PlaylistItem("MIYU 추천 곡");

        playlist_list.add(all_songs);
        playlist_list.add(recent_songs);
        playlist_list.add(like_songs);
        playlist_list.add(frequent_songs);
        playlist_list.add(miyu_songs);

        PlaylistAdapter adapter = new PlaylistAdapter(getActivity(), R.layout.listtextview, playlist_list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Songlist.class);
                intent.putExtra("playlist_position", position);

                startActivity(intent);
            }
        });

        return view;
    }


}



