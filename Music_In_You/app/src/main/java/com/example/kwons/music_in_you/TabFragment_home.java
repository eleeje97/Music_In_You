package com.example.kwons.music_in_you;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

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



        // chart view
        View pChart = getLayoutInflater().inflate(R.layout.emotion_pie_chart, null, false);
        RelativeLayout layout = pChart.findViewById(R.id.piechart_orgin);
        container.addView(pChart);

        return view;



    }



}
