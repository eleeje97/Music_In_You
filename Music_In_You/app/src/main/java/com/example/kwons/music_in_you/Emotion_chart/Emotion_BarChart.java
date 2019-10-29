package com.example.kwons.music_in_you.Emotion_chart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kwons.music_in_you.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


import java.util.ArrayList;
import java.util.List;

public class Emotion_BarChart extends Activity  {

    // Bar Chart
    private BarChart mChart;
    private SeekBar seekBar;
    private TextView textView;

    // 차트 데이터 셋을 담을 list
    private List<BarEntry> EntryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_bar_chart);

        // 전달받은 intent
        Intent intent = getIntent();

        // 전달받은 라벨
        String label = intent.getExtras().getString("emotion");

        mChart = (BarChart) findViewById(R.id.barchart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        // empty labels so that the names are spread evenly
        String[] labels = { "","1Week", "2Week", "3Week", "4Week", "5Week",""};
        IAxisValueFormatter xAxisFormatter = new LabelFormatter(mChart, labels);
        XAxis xAxis = mChart.getXAxis();
        //xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(xAxisFormatter);


        // Y축 설정
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(5, true);
        leftAxis.setAxisMinimum(0f); // Y축 최소 0부터 시작
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART); // Y축 수치 밖에 고정

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(true);

        float[] valOne = {0, 3, 5, 2, 8, 1, 0};



        // bar Data 수치
        ArrayList<BarEntry> barData = new ArrayList<>();

        for (int i = 0; i < valOne.length; i++) {
            barData.add(new BarEntry(i, valOne[i]));
        }

        // label_colors.xml에 저장된 string 배열을 가져옴
        String[] colors = getResources().getStringArray(R.array.label_color);


        // BarDataSet 생성
        BarDataSet barDataSet = new BarDataSet(barData, label);

        // 감정별로 그래프 색과 라벨 지정하기
        switch (label){
            case "Happy":
                barDataSet.setColor(Color.parseColor(colors[0])); // Happy일 때 색상
                break;

            case "Angry":
                barDataSet.setColor(Color.parseColor(colors[1])); // Angry일 때 색상
                break;

            case "Sad":
                barDataSet.setColor(Color.parseColor(colors[2])); // Sad일 때 색상
                break;

            case "Calm":
                barDataSet.setColor(Color.parseColor(colors[3])); // Calm일 때 색상
                break;
        }

        barDataSet.setHighlightEnabled(false);
        barDataSet.setDrawValues(false);


        // BarData
        BarData data = new BarData(barDataSet);
        float barWidth = 0.3f;
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        mChart.setData(data);
        mChart.setScaleEnabled(false);
        mChart.setVisibleXRangeMaximum(4f);
        mChart.invalidate();

    }


    private class LabelFormatter implements IAxisValueFormatter {

        String[] labels;
        BarLineChartBase<?> chart;

        LabelFormatter(BarLineChartBase<?> chart, String[] labels) {
            this.chart = chart;
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels[(int) value];
        }
    }


}