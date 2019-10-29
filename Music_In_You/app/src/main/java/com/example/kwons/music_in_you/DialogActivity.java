package com.example.kwons.music_in_you;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends Activity {

    private Button play, cancel;
    private ArrayList<MusicDTO> list; // 데이터를 넣은 리스트
    private int position; // 선택된 음악의 id를 담을 변수
    private Intent intent;

    static DialogActivity dialogActivity;
    Search_MusicActivity search_musicActivity = (Search_MusicActivity)Search_MusicActivity.search_musicActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        intent = getIntent(); // 인텐트 값을 받아옴
        play = (Button)findViewById(R.id.play_music);
        cancel = (Button)findViewById(R.id.canel);

        position = intent.getExtras().getInt("playlist_position");

        list = MainActivity.mainActivity.getMusicList(); // 음악 리스트 목록을 불러옴
        play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),list.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent_music = new Intent(DialogActivity.this , MusicPlayActivity.class);
                intent_music.putExtra("playlist_position", position);
                intent_music.putExtra("playlist", list);
                startActivity(intent_music);
                finish();
                //search_musicActivity.finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        return;
    }
}
