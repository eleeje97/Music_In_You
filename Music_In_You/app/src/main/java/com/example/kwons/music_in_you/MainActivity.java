package com.example.kwons.music_in_you;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // 권한 체크를 위한 객체
    PermissionCheck permission;


    // 녹음 시작/정지/재생 버튼
    Button startButton;
    Button stopButton;
    Button playButton;

    // 녹음 파일 저장할 경로 지정
    //final private static String RECORD_FILE = getFilesDir().getAbsolutePath() + "/MIYU/test_record";
    //final private static String RECORD_FILE = "/MIYU/test_record";
    File file;

    // 녹음기 객체
    MediaRecorder recorder;

    // Player 객체
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // permission 객체 생성;
        permission = new PermissionCheck(MainActivity.this);


        file = new File(getFilesDir(), "test_record");
        Log.i("file 경로: ", file.getAbsolutePath());


        /*
        // 파일 생성
        File saveFile = new File(RECORD_FILE); // 저장 경로
        // 폴더 생성
        if(!saveFile.exists()){ // 폴더 없을 경우
            saveFile.mkdir(); // 폴더 생성
        }
        */




        // 녹음 시작/정지/재생 버튼을 가져옴
        startButton = (Button)findViewById(R.id.start);
        stopButton = (Button)findViewById(R.id.stop);
        playButton = (Button)findViewById(R.id.play);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                boolean isRecordChecked = permission.isChecked("Record");

                if(isRecordChecked) {
                    // recorder가 null이 아니면, recorder를 비운다.
                    if(recorder != null) {
                        recorder.stop();
                        recorder.release();
                        recorder = null;
                    }



                    // recorder 객체 생성
                    recorder = new MediaRecorder();
                    Log.i("MediaRecorder 객체: ", recorder.toString());



                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 마이크를 통해 음성을 받는다.
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 파일 타입 설정
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 코덱 설정




                    recorder.setOutputFile(file.getAbsolutePath()); // 저장될 파일 위치 지정
                    //recorder.setOutputFile(RECORD_FILE);



                    try {
                        Toast.makeText(getApplicationContext(), "녹음을 시작합니다.", Toast.LENGTH_LONG).show();

                        recorder.prepare();
                        recorder.start();
                    } catch (Exception e) {
                        Log.e("SampleAudioRecorder", "Exception: ", e);
                    }
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 녹음 중인 recorder가 없으면 return
                if(recorder == null)
                    return;

                recorder.stop();
                recorder.release();;
                recorder = null;

                Toast.makeText(getApplicationContext(), "녹음이 중지되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playAudio();
            }
        });



    }

    private void playAudio() {
        try {
            closePlayer();

            player = new MediaPlayer();
            player.setDataSource(file.getAbsolutePath());
            player.prepare();
            player.start();

            Toast.makeText(this, "재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }


}
