package com.example.kwons.music_in_you.Emotion_chart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kwons.music_in_you.Login;
import com.example.kwons.music_in_you.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Emotion_PieChart extends Activity implements OnChartValueSelectedListener {

    public static Emotion_PieChart emotion_pieChart;

    private PieChart pieChart;
    public ArrayList<PieEntry> yValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_pie_chart);


        // 데이터 사용하기 위해서
        emotion_pieChart = Emotion_PieChart.this;






        pieChart = (PieChart) findViewById(R.id.piechart);
        //Toast.makeText(Emotion_PieChart.this,"Pie Chart",Toast.LENGTH_LONG).show();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("EMOTION");
        pieChart.setCenterTextSize(14);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        // Pie 차트 중간에 구멍 내려면 true
        pieChart.setDrawHoleEnabled(true);

        // Pie 차트 배경 색상
        pieChart.setHoleColor(Color.WHITE);

        // Pie 차트 크기 조정
        pieChart.setTransparentCircleRadius(55f);

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
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);

        /* PIE 그래프 데이터 SET */
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);


    }

    // 리스너 method
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        // Happy일 때,
        if (e.equalTo(yValues.get(0))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(0).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(0).getLabel());
            startActivity(intent);
        }

        // Angry일 때,
        else if(e.equalTo(yValues.get(1))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(1).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(1).getLabel());
            startActivity(intent);
        }

        // Sad일 때,
        else if(e.equalTo(yValues.get(2))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(2).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(2).getLabel());
            startActivity(intent);
        }

        // Calm일 때,
        else if(e.equalTo(yValues.get(3))) {
            //Toast.makeText(Emotion_PieChart.this, yValues.get(3).getLabel(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Emotion_BarChart.class);
            intent.putExtra("emotion",yValues.get(3).getLabel());
            startActivity(intent);
        }


    }

    @Override
    public void onNothingSelected() {

    }



}