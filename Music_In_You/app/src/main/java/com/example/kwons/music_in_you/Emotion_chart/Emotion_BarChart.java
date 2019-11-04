package com.example.kwons.music_in_you.Emotion_chart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.Random;

public class Emotion_BarChart extends Activity  {

    // Bar Chart
    private BarChart mChart;
    private SeekBar seekBar;
    private TextView textView;

    // 차트 데이터 셋을 담을 list
    private List<BarEntry> EntryData;


    // 글씨체
    Typeface typeface, typeface2;
    private final static String PATH_TO_WEATHER_FONT = "fonts/font1.otf";
    private final static String PATH_TO_WEATHER_FONT2 = "fonts/title_font.otf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_bar_chart);

        // 전달받은 intent
        Intent intent = getIntent();

        // 전달받은 라벨
        String label = intent.getExtras().getString("emotion");

        // 텍스트
        TextView emotion_ment = findViewById(R.id.emotion_commend);
        TextView barChart_name = findViewById(R.id.barChart_name);
        barChart_name.setTypeface(typeface2);
        // 감정별 멘트 저장
        String[] happy_ment = {"즐거운 한달 보내셨나요?\n" +
                "앞으로도 이번달처럼 행복한 일 가득하길 MIYU가 응원할게요!",
                               "웃으면 복이 온대요! 오늘부터 1일 30웃음 도전!!\n" +
                                       "MIYU는 1일 100웃음 한답니다! 하하하^__^",
                               "행복은 사소한 것에서부터 시작된답니다!\n" +
                                       "지금 당장 작은 일부터 시작해보는 것이 어떨까요!\n" +
                                       "MIYU는 언제나 당신을 응원합니다!",
                               "당신을 빛나게 하는 가장 쉬운 방법 알고 계시나요?\n" +
                                       "그것은 바로! ^__________^ 스마일!",
                               "얼굴 찌푸리지 말아요~ 모두가 힘들잖아요!\n" +
                                       "기쁨의 그 날 위해 함께 할 MIYU가 있잖아요!♡"};
        String[] sad_ment = {"적당한 슬픈은 우리에게 유익하답니다.\n" +
                "지금 이슬픔에 힘들어하지 말고 즐기세요!",
                "지친 마음과 외로움이 가득 한가요?\n" +
                "MIYU가 당신의 지친 마음을 달래드릴게요!\n" +
                "그리고 언제나 옆에 있을게요!",
                "지금 힘든 마음 때문에 너무 슬퍼하지 마세요!\n" +
                        "당신에겐 더욱 더 나은 미래가 있답니다!",
                "곁에 아무도 없다는 생각이 들 때, 주위를 둘러보세요!\n" +
                        "당신의 곁에는 당신을 아껴주고 사랑해주는 사람들로 \n" +
                        "가득할거에요! MIYU도 당신을 응원한답니다!^0^",
                "한번쯤은 '나'만 생각하는 이기심도 필요하답니다!\n" +
                        "내인생에 가장 소중한 존재는 당신이니까요!",
                            "지나간 일에 후회하고 슬퍼하는 것보다\n" +
                                    "앞으로의 '나'를 위한 시간을 갖는 것은 어떨까요!\n" +
                                    "MIYU는 그런 당신을 응원한답니다!^0^"};
        String[] angry_ment = {"화가 나는 것이 당연하고, 그런 상황에서 눈물 나는 것이 당연한 거에요! 당신이 약해서 그런 것이 아니랍니다.",
                "다른 사람의 어리석은 행동에 화를 내어 당신의 마음을 아프게 하지 마세요!" +
                        "당신이 아프면 MIYU도 아프답니다.",
                "기분 나쁘고 화나는 일은 우리 같이 훌훌 털어버리고!\n" +
                        "더 좋은 일, 행복한 일만 만들어 봐요!^ㅇ^",
                "분노를 가라앉히는 좋은 방법은 아무것도 하지 않고 가만히 있는 것이랍니다! \n" +
                        "MIYU와 함께 노래를 들으며 힐링타임을 가져볼까요?",
                "짜증을 내어서 무엇하나, 성화를 바치어 무엇하나\n" +
                "속상한 일도 하도 많으니 놀기도 하면서 살아가세\n" +
                "        \t\t\t- 태평가 일부-",
                "사람에게 쓸모없는 감정은 없습니다. 단지 조절해야할 감정이 있을 뿐이죠! MIYU와 함께 음악을 들으며 감정을 다스려보아요!"
        };
        String[] calm_ment = {"세상에서 가장 현명한 사람은 감정을 조절할 수 있는 사람이래요! " +
                "바로 당신이 그 현명한 사람이 아닐까요?",
                "","","",""};



        // 랜덤
        Random random = new Random();
        int emotion_data =0;

        // 글씨체
        typeface = Typeface.createFromAsset(getAssets(), PATH_TO_WEATHER_FONT);
        typeface2 = Typeface.createFromAsset(getAssets(), PATH_TO_WEATHER_FONT2);
        // 막대 차트
        mChart = (BarChart) findViewById(R.id.barchart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        // empty labels so that the names are spread evenly
        String[] labels = { "","1Week", "2Week", "3Week", "4Week",""};
        IAxisValueFormatter xAxisFormatter = new LabelFormatter(mChart, labels);
        XAxis xAxis = mChart.getXAxis();
        //xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTypeface(typeface2);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(xAxisFormatter);


        // Y축 설정
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(5, true);
        leftAxis.setAxisMinimum(0f); // Y축 최소 0부터 시작
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART); // Y축 수치 밖에 고정

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(true);

        float[] valOne = {0, 3, 5, 2, 8, 0};



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
                emotion_data = random.nextInt(happy_ment.length);
                barDataSet.setColor(Color.parseColor(colors[0])); // Happy일 때 색상
                emotion_ment.setText(happy_ment[emotion_data]);
                emotion_ment.setTypeface(typeface);
                break;

            case "Angry":
                emotion_data = random.nextInt(angry_ment.length);
                barDataSet.setColor(Color.parseColor(colors[1])); // Angry일 때 색상
                emotion_ment.setText(angry_ment[emotion_data]);
                emotion_ment.setTypeface(typeface);
                break;

            case "Sad":
                emotion_data = random.nextInt(sad_ment.length);
                barDataSet.setColor(Color.parseColor(colors[2])); // Sad일 때 색상
                emotion_ment.setText(sad_ment[emotion_data]);
                emotion_ment.setTypeface(typeface);
                break;

            case "Calm":
                emotion_data = random.nextInt(sad_ment.length);
                barDataSet.setColor(Color.parseColor(colors[3])); // Calm일 때 색상
                emotion_ment.setText(calm_ment[emotion_data]);
                emotion_ment.setTypeface(typeface);
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




        // 감정별 멘트



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