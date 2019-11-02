package com.example.kwons.music_in_you;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwons.music_in_you.Database.DBOpenHelper;
import com.example.kwons.music_in_you.Emotion_chart.Emotion_BarChart;
import com.example.kwons.music_in_you.Emotion_chart.Emotion_PieChart;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class TabFragment_home extends Fragment implements OnChartValueSelectedListener, View.OnClickListener {

    Emotion_PieChart emotion_pieChart = (Emotion_PieChart)Emotion_PieChart.emotion_pieChart;


    public ArrayList<PieEntry> yValues;
    TextView textView;
    ImageView mic;

    String name;

    // 재생목록 변수들 선언
    LinearLayout all_songs, like_songs, frequent_songs;
    TextView all_songs_count, like_songs_count, frequent_songs_count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.tab_home,container,false);

        Intent intent = getActivity().getIntent();

        // 넘어온 이름이 없으면 공백으로 출력
        /*if(intent.getExtras().getString("NAME") == null){
            name = "";
        }
        else{name = intent.getExtras().getString("NAME");}*/

        // 재생목록
        all_songs = view.findViewById(R.id.all_songs);
        like_songs = view.findViewById(R.id.like_songs);
        frequent_songs = view.findViewById(R.id.frequent_songs);

        all_songs_count = view.findViewById(R.id.all_songs_count);
        like_songs_count = view.findViewById(R.id.like_songs_count);
        frequent_songs_count = view.findViewById(R.id.frequent_songs_count);

        ArrayList<MusicDTO> list; // 전체 노래 리스트
        ArrayList<MusicDTO> playlist; // 재생목록 담아올 리스트
        Bitmap bitmap; // 앨범 사진 가져올 비트맵
        BitmapDrawable bitmapDrawable; // 비트맵을 Drawable로 바꿔줄 변수
        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기
        playlist = new ArrayList<>();
        DBOpenHelper mDbOpenHelper = new DBOpenHelper(view.getContext());
        mDbOpenHelper.open();
        Cursor iCursor;


        // 재생목록에 앨범 사진 넣기
        // (1) 모든 노래
        bitmap = BitmapFactory.decodeFile(MusicPlayActivity.getCoverArtPath(
                Long.parseLong(list.get(0).getAlbumId()),
                view.getContext()));
        if(bitmap != null) {
            bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            all_songs.setBackground(bitmapDrawable);
            all_songs_count.setText(list.size() + all_songs_count.getText().toString());
        }
        else{
            all_songs.setBackground(getActivity().getDrawable(R.drawable.default_music_album));
            all_songs_count.setText(list.size() + all_songs_count.getText().toString());
        }
        // (2) 좋아요 곡
        iCursor = mDbOpenHelper.selectLoveSongs();
        while (iCursor.moveToNext()) {
            int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
            playlist.add(list.get(idx));
        }

        if(playlist.size() > 0) {
            bitmap = BitmapFactory.decodeFile(MusicPlayActivity.getCoverArtPath(
                    Long.parseLong(playlist.get(0).getAlbumId()),
                    view.getContext()));
            bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            like_songs.setBackground(bitmapDrawable);
            like_songs_count.setText(playlist.size() + like_songs_count.getText().toString());
        } else {
            like_songs_count.setText(0 + like_songs_count.getText().toString());
        }

        // (3) 많이 재생한 곡
        playlist.clear();
        iCursor = mDbOpenHelper.selectFrequentSongs();
        while (iCursor.moveToNext()) {
            int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
            playlist.add(list.get(idx));
        }
        bitmap = BitmapFactory.decodeFile(MusicPlayActivity.getCoverArtPath(
                Long.parseLong(playlist.get(0).getAlbumId()),
                view.getContext()));
        bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        frequent_songs.setBackground(bitmapDrawable);
        frequent_songs_count.setText(playlist.size() + frequent_songs_count.getText().toString());

        iCursor.close();
        mDbOpenHelper.close();

        // 재생목록에 리스너 달기
        all_songs.setOnClickListener(this);
        like_songs.setOnClickListener(this);
        frequent_songs.setOnClickListener(this);


        textView = view.findViewById(R.id.mic_msg);
        //textView.setText(name + textView.getText().toString());


        mic = view.findViewById(R.id.main_mic);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),VoiceDetection.class);
                startActivity(intent);
            }
        });

        //Log.i("MIYU", getView().toString());
        //View pChart = inflater.inflate(R.layout.emotion_pie_chart,container);

        //View pChart = getView().findViewById(R.id.include_pie_chart);
        PieChart pieChart = view.findViewById(R.id.piechart);

        Toast.makeText(getContext(),"Pie Chart",Toast.LENGTH_LONG).show();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("EMOTION");
        pieChart.setCenterTextSize(12);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        // Pie 차트 중간에 구멍 내려면 true
        pieChart.setDrawHoleEnabled(true);

        // Pie 차트 배경 색상
        //pieChart.setHoleColor(Color.WHITE);
        pieChart.setCenterTextColor(Color.BLACK);


        // Pie 차트 크기 조정
        pieChart.setTransparentCircleRadius(100f);

        // Pie 차트 클릭 및 리스너 추가
        pieChart.setClickable(true);
        pieChart.setOnChartValueSelectedListener(this);



        /* PIE 그래프 그리기 */
        yValues = new ArrayList<PieEntry>();



        // value가 비율을 나타냄
        // 우리는 안드로이드 내부DB 연결해서 값 넣어주면 됨
        // label은 그래프 상에서 보여질 이름
        yValues.add(new PieEntry(21f, "Happy"));
        yValues.add(new PieEntry(14f, "Angry"));
        //yValues.add(new PieEntry(4f, "Normal"));
        yValues.add(new PieEntry(40f, "Sad"));
        yValues.add(new PieEntry(35f, "Calm"));



        // 차트 애니메이션
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // 하단에 나타낼 그래프 섹션별 목록
        PieDataSet dataSet = new PieDataSet(yValues, "Emotion");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        /* PIE 그래프 데이터 SET */
        PieData data = new PieData((dataSet));
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);


        return view;
    }

    // 리스너 method
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        // Happy일 때,
        if (e.equalTo(yValues.get(0))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(0).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(0).getLabel());
            startActivity(intent);
        }

        // Angry일 때,
        else if(e.equalTo(yValues.get(1))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(1).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(1).getLabel());
            startActivity(intent);
        }

        // Sad일 때,
        else if(e.equalTo(yValues.get(2))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(2).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(2).getLabel());
            startActivity(intent);
        }

        // Calm일 때,
        else if(e.equalTo(yValues.get(3))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(3).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(3).getLabel());
            startActivity(intent);
        }


    }

    @Override
    public void onNothingSelected() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        switch (id) {
            case R.id.all_songs:
                intent = new Intent(getContext(), Songlist.class);
                intent.putExtra("playlist_position", 0);
                startActivity(intent);
                break;
            case R.id.like_songs:
                intent = new Intent(getContext(), Songlist.class);
                intent.putExtra("playlist_position", 1);
                startActivity(intent);
                break;
            case R.id.frequent_songs:
                intent = new Intent(getContext(), Songlist.class);
                intent.putExtra("playlist_position", 2);
                startActivity(intent);
                break;
        }
    }
}
