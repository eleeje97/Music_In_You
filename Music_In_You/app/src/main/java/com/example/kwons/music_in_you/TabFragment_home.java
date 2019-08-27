package com.example.kwons.music_in_you;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TabFragment_home extends Fragment {

    TextView textView;
    ImageView mic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_home,container,false);

        textView = view.findViewById(R.id.mic_msg);
        mic = view.findViewById(R.id.main_mic);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),VoiceDetection.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
