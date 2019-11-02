package com.example.kwons.music_in_you;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kwons.music_in_you.Service.MusicService;

import java.util.ArrayList;

public class TabFragment_setting extends Fragment {
    Button button;
    ArrayList<MusicDTO> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.tab_setting,container,false);

        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기


        /** 음악 서비스 테스트 **/
        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MusicService.class);
                intent.putExtra(MusicService.MESSAGE_KEY, true);

                intent.putExtra(MusicService.MUSIC_DTO, list.get(5));
                getActivity().startService(intent);
            }
        });



        return view;
    }
}
