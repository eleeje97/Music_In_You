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

import static com.example.kwons.music_in_you.PermissionCheck.MY_PERMISSION_RECORD;

public class MainActivity extends AppCompatActivity {

    // 권한 체크를 위한 객체
    PermissionCheck permission;


    // 녹음 시작/정지/재생 버튼
    Button startButton;
    Button stopButton;
    Button playButton;

    // 녹음 파일
    File file;

    // 녹음기 객체
    MediaRecorder recorder;

    // Player 객체
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // permission 객체 생성
        permission = new PermissionCheck(MainActivity.this);


        // getFilesDir() : /data/user/0/com.example.kwons.music_in_you/files
        file = new File(getFilesDir(), "test_record");
        Log.i("file 경로: ", file.getAbsolutePath());


        // 녹음 시작/정지/재생 버튼을 가져옴
        startButton = (Button)findViewById(R.id.start);
        stopButton = (Button)findViewById(R.id.stop);
        playButton = (Button)findViewById(R.id.play);


        // 녹음 시작 버튼 리스너
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // RECORD 권한이 허용되어 있는지 확인하는 변수
                boolean isRecordChecked = permission.isChecked("Record");

                if(isRecordChecked) {
                    recordVoice();
                }

            }
        });

        // 녹음 중지 버튼 리스너
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // 녹음 중인 recorder가 없으면 return
                if(recorder == null)
                    return;

                recorder.stop();
                recorder.release();
                recorder = null;

                Toast.makeText(getApplicationContext(), "녹음이 중지되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


        // 재생 버튼 리스너
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playAudio();
            }
        });



    }

    // 녹음 시작 메소드
    private void recordVoice() {
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

    // 재생 메소드
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

    // player 자원 해제 메소드
    private void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }


    /**
     * PermissionCheck의 isChecked() 함수 후 호출되는 함수, MainActivity.this를 파라미터로 전달하여 생성했기에 이쪽에서 호출됨
     * @param requestCode : ActivityCompat.requestPermission의 세 번째 파라미터
     * @param permissions : 권한들에 대한 String 값 (Manifest.permission.XXX)
     * @param grantResults : ActivityCompat.requestPermissions의 두 번째 파라미터에 들어간 요청 권한 들에 대한 결과 값
     **/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("PermissionResult", "CALL");
        switch(requestCode) {
            case MY_PERMISSION_RECORD:
                for(int i=0; i<grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(MainActivity.this, "해당 권한을 허용해야 합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        recordVoice();
                    }
                }

                break;

        }
    }



}
