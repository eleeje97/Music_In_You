package com.example.kwons.music_in_you;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment_home extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_home,container,false);

        //textView = view.findViewById(R.id.tv);
        //textView.setText("Home 화면!!!!!!!!!");

        return view;
    }
}
