package com.example.kwons.music_in_you;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwons.music_in_you.Database.DBOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class TabFragment_setting extends Fragment {
    TextView music_classification_btn;
    ArrayList<MusicDTO> list;

    DBOpenHelper mDbOpenHelper;

    @Override
    public View onCreateView(LayoutInflater     inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.tab_setting,container,false);

        music_classification_btn = view.findViewById(R.id.music_classification_btn);
        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기

        music_classification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMusicDB();
                showDB();
                calcEmotion();
                showDB();
                Toast.makeText(getContext(), "음악 분류 완료", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    void createMusicDB() {
        mDbOpenHelper = new DBOpenHelper(getContext());
        mDbOpenHelper.open();

        mDbOpenHelper.deleteTable();
        mDbOpenHelper.create(); // 테이블 생성


        /***************************************************************/

        //음악 감정 분류 할 코드
        mDbOpenHelper.insertColumn(0, list.get(0).getId(), 1, 0, 0, 0, 0, 0); //fancy
        mDbOpenHelper.insertColumn(1, list.get(1).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(2, list.get(2).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(3, list.get(3).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(4, list.get(4).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(5, list.get(5).getId(), 0, 0, 1, 0, 0, 0); //hill this love
        mDbOpenHelper.insertColumn(6, list.get(6).getId(), 1, 0, 0, 0, 0, 0); //2002
        mDbOpenHelper.insertColumn(7, list.get(7).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(8, list.get(8).getId(), 0, 0, 0, 1, 0, 0); // 그때가 좋았어
        mDbOpenHelper.insertColumn(9, list.get(9).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(10, list.get(10).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(11, list.get(11).getId(), 0, 0, 0, 1, 0, 0); // bad guy
        mDbOpenHelper.insertColumn(12, list.get(12).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(13, list.get(13).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(14, list.get(14).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(15, list.get(15).getId(), 0, 0, 1, 0, 0, 0); // dionysus
        mDbOpenHelper.insertColumn(16, list.get(16).getId(), 0, 1, 0, 0, 0, 0); // 사계
        mDbOpenHelper.insertColumn(17, list.get(17).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(18, list.get(18).getId(), 0, 0, 0, 1, 0, 0); // 봄날
        mDbOpenHelper.insertColumn(19, list.get(19).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(20, list.get(20).getId(), 0, 0, 1, 0, 0, 0); // idol
        mDbOpenHelper.insertColumn(21, list.get(21).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(22, list.get(22).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(23, list.get(23).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(24, list.get(24).getId(), 0, 0, 0, 1, 0, 0); // 모든날 모든순간
        mDbOpenHelper.insertColumn(25, list.get(25).getId(), 0, 0, 1, 0, 0, 0);
        mDbOpenHelper.insertColumn(26, list.get(26).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(27, list.get(27).getId(), 1, 0, 0, 0, 0, 0); //고고베베
        mDbOpenHelper.insertColumn(28, list.get(28).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(29, list.get(29).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(30, list.get(30).getId(), 0, 0, 0, 1, 0, 0);// 너를 만나
        mDbOpenHelper.insertColumn(31, list.get(31).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(32, list.get(32).getId(), 0, 0, 0, 1, 0, 0); // 술이달다
        mDbOpenHelper.insertColumn(33, list.get(33).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(34, list.get(34).getId(), 1, 0, 0, 0, 0, 0);// 멍청이
        mDbOpenHelper.insertColumn(35, list.get(35).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(36, list.get(36).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(37, list.get(37).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(38, list.get(38).getId(), 1, 0, 0, 0, 0, 0); // 7ring
        mDbOpenHelper.insertColumn(39, list.get(39).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(40, list.get(40).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(41, list.get(41).getId(), 0, 0, 0, 1, 0, 0); //신청곡
        mDbOpenHelper.insertColumn(42, list.get(42).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(43, list.get(43).getId(), 0, 0, 1, 0, 0, 0);
        mDbOpenHelper.insertColumn(44, list.get(44).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(45, list.get(45).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(46, list.get(46).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(47, list.get(47).getId(), 1, 0, 0, 0, 0, 0); // tempo
        mDbOpenHelper.insertColumn(48, list.get(48).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(49, list.get(49).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(50, list.get(50).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(51, list.get(51).getId(), 0, 1, 0, 0, 0, 0); // 여자친구
        mDbOpenHelper.insertColumn(52, list.get(52).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(53, list.get(53).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(54, list.get(54).getId(), 1, 0, 0, 0, 0, 0); // 삐삐
        mDbOpenHelper.insertColumn(55, list.get(55).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(56, list.get(56).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(57, list.get(57).getId(), 0, 1, 0, 0, 0, 0); // 있어줄래
        mDbOpenHelper.insertColumn(58, list.get(58).getId(), 0, 0, 1, 0, 0, 0); // 아낙네
        mDbOpenHelper.insertColumn(59, list.get(59).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(60, list.get(60).getId(), 1, 0, 0, 0, 0, 0); // 가을타나봐
        mDbOpenHelper.insertColumn(61, list.get(61).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(62, list.get(62).getId(), 0, 0, 0, 1, 0, 0); // 그리워하다
        mDbOpenHelper.insertColumn(63, list.get(63).getId(), 0, 1, 0, 0, 0, 0); // 하루도 그대를
        mDbOpenHelper.insertColumn(64, list.get(64).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(65, list.get(65).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(66, list.get(66).getId(), 1, 0, 0, 0, 0, 0); //라비앙
        mDbOpenHelper.insertColumn(67, list.get(67).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(68, list.get(68).getId(), 0, 0, 1, 0, 0, 0); // 작은 것들을 위한
        mDbOpenHelper.insertColumn(69, list.get(69).getId(), 0, 1, 0, 0, 0, 0); // goodbye
        mDbOpenHelper.insertColumn(70, list.get(70).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(71, list.get(71).getId(), 1, 0, 0, 0, 0, 0); // 워크홀릭
        mDbOpenHelper.insertColumn(72, list.get(72).getId(), 0, 1, 0, 0, 0, 0); // 시든꽃
        mDbOpenHelper.insertColumn(73, list.get(73).getId(), 0, 0, 0, 1, 0, 0); // 떨낙
        mDbOpenHelper.insertColumn(74, list.get(74).getId(), 0, 1, 0, 0, 0, 0); // 니소식
        mDbOpenHelper.insertColumn(75, list.get(75).getId(), 0, 0, 0, 1, 0, 0); // 허전해
        mDbOpenHelper.insertColumn(76, list.get(76).getId(), 1, 0, 0, 0, 0, 0); // 내생가장
        mDbOpenHelper.insertColumn(77, list.get(77).getId(), 0, 1, 0, 0, 0, 0); //포장마차
        mDbOpenHelper.insertColumn(78, list.get(78).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(79, list.get(79).getId(), 1, 0, 0, 0, 0, 0); // feel special
        mDbOpenHelper.insertColumn(80, list.get(80).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(81, list.get(81).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(82, list.get(82).getId(), 0, 1, 0, 0, 0, 0); // 사식
        mDbOpenHelper.insertColumn(83, list.get(83).getId(), 0, 0, 0, 1, 0, 0); // 첸노래
        mDbOpenHelper.insertColumn(84, list.get(84).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(85, list.get(85).getId(), 0, 0, 1, 0, 0, 0); // 창모
        mDbOpenHelper.insertColumn(86, list.get(86).getId(), 0, 1, 0, 0, 0, 0); //그래
        mDbOpenHelper.insertColumn(87, list.get(87).getId(), 0, 1, 0, 0, 0, 0); //다만날
        mDbOpenHelper.insertColumn(88, list.get(88).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(89, list.get(89).getId(), 0, 1, 0, 0, 0, 0); //헤고
        mDbOpenHelper.insertColumn(90, list.get(90).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(91, list.get(91).getId(), 0, 0, 0, 1, 0, 0);
        mDbOpenHelper.insertColumn(92, list.get(92).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(93, list.get(93).getId(), 0, 1, 0, 0, 0, 0); //솔직
        mDbOpenHelper.insertColumn(94, list.get(94).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(95, list.get(95).getId(), 0, 1, 0, 0, 0, 0); // 펀치
        mDbOpenHelper.insertColumn(96, list.get(96).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(97, list.get(97).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(98, list.get(98).getId(), 1, 0, 0, 0, 0, 0); // icy
        mDbOpenHelper.insertColumn(99, list.get(99).getId(), 0, 0, 1, 0, 0, 0);
        mDbOpenHelper.insertColumn(100, list.get(100).getId(), 0, 0, 1, 0, 0, 0);
        mDbOpenHelper.insertColumn(101, list.get(101).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(102, list.get(102).getId(), 0, 0, 0, 1, 0, 0); // 첸
        mDbOpenHelper.insertColumn(103, list.get(103).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(104, list.get(104).getId(), 0, 0, 0, 1, 0, 0); // 달
        mDbOpenHelper.insertColumn(105, list.get(105).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(106, list.get(106).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(107, list.get(107).getId(), 0, 1, 0, 0, 0, 0); // 사계
        mDbOpenHelper.insertColumn(108, list.get(108).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(109, list.get(109).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(110, list.get(110).getId(), 0, 0, 1, 0, 0, 0); // 하하하
        mDbOpenHelper.insertColumn(111, list.get(111).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(112, list.get(112).getId(), 1, 0, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(113, list.get(113).getId(), 0, 1, 0, 0, 0, 0);
        mDbOpenHelper.insertColumn(114, list.get(114).getId(), 0, 1, 0, 0, 0, 0); // 새사랑
        mDbOpenHelper.insertColumn(115, list.get(115).getId(), 1, 0, 0, 0, 0, 0); //샤넬
        mDbOpenHelper.insertColumn(116, list.get(116).getId(), 1, 0, 0, 0, 0, 0); //헌ㄷ,ㄹ
        mDbOpenHelper.insertColumn(117, list.get(117).getId(), 0, 1, 0, 0, 0, 0); // 조취
        mDbOpenHelper.insertColumn(118, list.get(118).getId(), 0, 1, 0, 0, 0, 0); // 있어줘요
        mDbOpenHelper.insertColumn(119, list.get(119).getId(), 0, 1, 0, 0, 0, 0); // 사랑
        mDbOpenHelper.insertColumn(120, list.get(120).getId(), 0, 0, 0, 1, 0, 0); // 안녕
        mDbOpenHelper.insertColumn(121, list.get(121).getId(), 0, 1, 0, 0, 0, 0); // 오늘도

        /***************************************************************/

        //DBOpenHelper mDbOpenHelperr = new DBOpenHelper(getContext());
        //mDbOpenHelperr.open();
        //mDbOpenHelperr.insertColumn(j, list.get(j).getId(), 1, 0, 0, 0, 0, 0);
        //putEmotion();


        ArrayList<Double> emotions = new ArrayList<>();
        Cursor iCursor = mDbOpenHelper.selectColumns();




        while (iCursor.moveToNext()) {
            /**
            String[] emotion_label = {"happy", "sad", "aggressive", "relaxed"};


            String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));
            double happy = iCursor.getDouble(iCursor.getColumnIndex("happy"));
            double sad = iCursor.getDouble(iCursor.getColumnIndex("sad"));
            double aggressive = iCursor.getDouble(iCursor.getColumnIndex("aggressive"));
            double relaxed = iCursor.getDouble(iCursor.getColumnIndex("relaxed"));

            emotions.add(happy);
            emotions.add(sad);
            emotions.add(aggressive);
            emotions.add(relaxed);

            Log.e("emotions 조회", emotions.get(0) + ", " + emotions.get(1) + ", " + emotions.get(2) + ", " + emotions.get(3));

            Double emotion = Collections.max(emotions);
            mDbOpenHelper.updateEmotionColumn(song_id, emotion_label[emotions.indexOf(emotion)]);
            Log.e("emotion 조회", emotion_label[emotions.indexOf(emotion)]);
             **/


        }

        mDbOpenHelper.close();

    }

    /* DB 조회 */
    void showDB() {
        DBOpenHelper mDbOpenHelper = new DBOpenHelper(getContext());
        mDbOpenHelper.open();

        Cursor iCursor = mDbOpenHelper.selectColumns();

        while (iCursor.moveToNext()) {
            int idx = iCursor.getInt(iCursor.getColumnIndex("idx"));
            String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));
            double happy = iCursor.getDouble(iCursor.getColumnIndex("happy"));
            double sad = iCursor.getDouble(iCursor.getColumnIndex("sad"));
            double aggressive = iCursor.getDouble(iCursor.getColumnIndex("aggressive"));
            double relaxed = iCursor.getDouble(iCursor.getColumnIndex("relaxed"));
            String emotion = iCursor.getString(iCursor.getColumnIndex("emotion"));
            int love = iCursor.getInt(iCursor.getColumnIndex("love"));
            int count = iCursor.getInt(iCursor.getColumnIndex("count"));


            String Result = "idx = " + idx + ", song_id = " + song_id + ", happy = " + happy + ", sad = " + sad + ", aggressive = " + aggressive + ", relaxed = " + relaxed + ", emotion = " + emotion + ", love = " + love + ", count = " + count;
            Log.e("DB조회", Result);
        }

        iCursor.close();
        mDbOpenHelper.close();
    }

    void calcEmotion() {
        DBOpenHelper mDbOpenHelper = new DBOpenHelper(getContext());
        mDbOpenHelper.open();

        ArrayList<Double> emotions = new ArrayList<>();
        Cursor iCursor = mDbOpenHelper.selectColumns();


        while (iCursor.moveToNext()) {

             String[] emotion_label = {"happy", "sad", "aggressive", "relaxed"};


             String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));
             double happy = iCursor.getDouble(iCursor.getColumnIndex("happy"));
             double sad = iCursor.getDouble(iCursor.getColumnIndex("sad"));
             double aggressive = iCursor.getDouble(iCursor.getColumnIndex("aggressive"));
             double relaxed = iCursor.getDouble(iCursor.getColumnIndex("relaxed"));

             emotions.add(happy);
             emotions.add(sad);
             emotions.add(aggressive);
             emotions.add(relaxed);

             Log.e("emotions 조회", emotions.get(0) + ", " + emotions.get(1) + ", " + emotions.get(2) + ", " + emotions.get(3));

             Double emotion = Collections.max(emotions);
             mDbOpenHelper.updateEmotionColumn(song_id, emotion_label[emotions.indexOf(emotion)]);
             Log.e("emotion 조회", emotion_label[emotions.indexOf(emotion)]);



        }

        mDbOpenHelper.close();
    }

}
