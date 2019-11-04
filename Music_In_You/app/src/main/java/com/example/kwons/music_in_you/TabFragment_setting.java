package com.example.kwons.music_in_you;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kwons.music_in_you.Database.DBOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class TabFragment_setting extends Fragment {
    TextView music_classification_btn;
    ArrayList<MusicDTO> list;

    DBOpenHelper mDbOpenHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.tab_setting,container,false);

        music_classification_btn = view.findViewById(R.id.music_classification_btn);
        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 가져오기

        music_classification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMusicDB();
                showDB();
            }
        });


        return view;
    }


    void createMusicDB() {
        mDbOpenHelper = new DBOpenHelper(getContext());
        mDbOpenHelper.open();

        mDbOpenHelper.deleteTable();
        mDbOpenHelper.create(); // 테이블 생성

        int i = 0;


        /***************************************************************/

        //음악 감정 분류 할 코드
        //mDbOpenHelper.insertColumn(0, list.get(0).getId(), 1, 0, 0, 0, 0, 0);

        /***************************************************************/

        for (int j = 0; j < list.size(); j++) {
            mDbOpenHelper.insertColumn(j, list.get(j).getId(), 1, 0, 0, 0, 0, 0);
            //putEmotion();
            ArrayList<Double> emotions = new ArrayList<>();
            Cursor iCursor = mDbOpenHelper.selectColumns();

            String[] emotion_label = {"happy", "sad", "aggressive", "relaxed"};

            while (iCursor.moveToNext()) {
                String song_id = iCursor.getString(iCursor.getColumnIndex("song_id"));
                double happy = iCursor.getDouble(iCursor.getColumnIndex("happy"));
                double sad = iCursor.getDouble(iCursor.getColumnIndex("sad"));
                double aggressive = iCursor.getDouble(iCursor.getColumnIndex("aggressive"));
                double relaxed = iCursor.getDouble(iCursor.getColumnIndex("relaxed"));

                emotions.add(happy);
                emotions.add(sad);
                emotions.add(aggressive);
                emotions.add(relaxed);


                Double emotion = Collections.max(emotions);
                mDbOpenHelper.updateEmotionColumn(song_id, emotion_label[emotions.indexOf(emotion)]);
            }


            i++;
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


}
