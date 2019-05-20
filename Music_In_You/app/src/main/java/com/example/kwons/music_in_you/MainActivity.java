package com.example.kwons.music_in_you;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static com.example.kwons.music_in_you.PermissionCheck.MY_PERMISSION_RECORD;

public class MainActivity extends AppCompatActivity {

    // 로깅을 위한 TAG
    final static String TAG = "[MIYU]";

    // 권한 체크를 위한 객체
    PermissionCheck permission;


    // 녹음 시작/정지/재생 버튼
    Button startButton;
    Button stopButton;
    Button playButton;

    // 녹음 파일
    File recordFile;

    // Recorder 객체
    MediaRecorder recorder;

    // Player 객체
    MediaPlayer player;


    // 파일 업로드 버튼
    Button uploadButton;


    // 감정분석 결과를 보여줄 TextView
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // permission 객체 생성
        permission = new PermissionCheck(MainActivity.this);


        // 녹음파일이 저장될 경로 지정
        // getFilesDir() : /data/user/0/com.example.kwons.music_in_you/files
        recordFile = new File(getFilesDir(), "test_record");
        Log.i(TAG, "recordFile 경로 - " + recordFile.getAbsolutePath());


        // 녹음 시작/정지/재생 버튼을 가져옴
        startButton = (Button)findViewById(R.id.start);
        stopButton = (Button)findViewById(R.id.stop);
        playButton = (Button)findViewById(R.id.play);

        // 파일 업로드 버튼을 가져옴
        uploadButton = (Button)findViewById(R.id.upload);

        // 감정분석 결과를 보여줄 TextView 가져옴
        resultTextView = (TextView) findViewById(R.id.result);



        // 녹음 시작 버튼 리스너
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // RECORD 권한이 허용되어 있는지 확인하는 변수
                boolean isRecordChecked = permission.isChecked("Record");

                // 권한이 허용되어 있다면 녹음시작
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

                // 녹음을 중지하고 Recorder 자원해제
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



        // 업로드 버튼 리스너
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // files 디렉터리에 있는 파일 리스트 출력
                Log.i(TAG, "리스트 개수: " + getFilesDir().list().length);
                for(int i = 0; i < getFilesDir().list().length; i ++) {
                    Log.i(TAG, getFilesDir().list()[i]);
                }


                // 녹음파일을 서버로 업로드
                HttpMultiPart(recordFile);


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


        // recorder Setting
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 마이크를 통해 음성을 받는다.
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 파일 타입 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 코덱 설정
        recorder.setOutputFile(recordFile.getAbsolutePath()); // 저장될 파일 위치 지정


        // 녹음 시작
        try {
            Toast.makeText(getApplicationContext(), "녹음을 시작합니다.", Toast.LENGTH_LONG).show();

            recorder.prepare();
            recorder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 재생 메소드
    private void playAudio() {
        try {
            closePlayer();

            // player 객체 생성
            player = new MediaPlayer();

            // 재생할 파일 경로 지정
            player.setDataSource(recordFile.getAbsolutePath());

            // 재생 시작
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


    // 녹음파일을 서버에 업로드하고 감정분석 결과를 받아오는 메소드
    private void HttpMultiPart(final File file){

        // 백그라운드에서 실행하기 위해 비동기화 방식 사용
        new AsyncTask<Void, Void, JSONObject>(){

            // Background에서 실행되는 메소드
            @Override
            protected JSONObject doInBackground(Void... voids) {

                String boundary = "^-----^";
                String LINE_FEED = "\r\n";
                String charset = "UTF-8";
                OutputStream outputStream;
                PrintWriter writer;

                // 서버에서 내려받은 결과 JSON
                JSONObject result = null;
                try{

                    // 서버 URL 지정 및 Connection Open
                    URL url = new URL("http://13.125.247.188/upload/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Log.i(TAG, "URL Connection DONE!");

                    // Connection Setting
                    connection.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary); // 형식, 캐릭터셋 속성 지정
                    connection.setRequestMethod("POST"); // POST방식으로 전송
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setConnectTimeout(15000);

                    Log.i(TAG, "Connection Setting DONE!");

                    outputStream = connection.getOutputStream();
                    writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);


                    /** Body에 데이터를 넣어줘야 할 경우 없으면 Pass **/
                    writer.append("--" + boundary).append(LINE_FEED);
                    writer.append("Content-Disposition: form-data; name=\"데이터 키값\"").append(LINE_FEED);
                    writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                    writer.append(LINE_FEED);
                    writer.append("데이터값").append(LINE_FEED);
                    writer.flush();

                    Log.i(TAG, "Data in Body DONE!");


                    /** 파일 데이터를 넣는 부분**/
                    writer.append("--" + boundary).append(LINE_FEED);
                    writer.append("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + file.getName() + "\"").append(LINE_FEED);
                    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
                    writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                    writer.append(LINE_FEED);
                    writer.flush();

                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[(int)file.length()];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                    inputStream.close();
                    writer.append(LINE_FEED);
                    writer.flush();

                    writer.append("--" + boundary + "--").append(LINE_FEED);
                    writer.close();

                    Log.i(TAG, "File Transfer DONE!");


                    // HTTP 상태 코드
                    int responseCode = connection.getResponseCode();
                    Log.i(TAG, "responseCode - " + responseCode);


                    /** HTTP 상태코드 의미
                     * HTTP_OK(200): POST인 경우, 수행 결과에 대한 리소스가 메시지 바디에 전송되었습니다.
                     * HTTP_CREATED: 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성되었습니다.
                     **/
                    // responseCode가 HTTP_OK(200)이거나 HTTP_CREATED(201)이면 결과를 리턴받아온다.
                    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        try {
                            result = new JSONObject(response.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i(TAG, "Result Return SUCCESS!");

                    } else {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        result = new JSONObject(response.toString());

                        Log.i(TAG, "Result returned FAIL");
                    }
                } catch (ConnectException e) {
                    Log.e(TAG, "ConnectException");
                    e.printStackTrace();


                } catch (Exception e){
                    e.printStackTrace();
                }

                return result;
            }

            // doInBackground() 메소드가 끝나고 호출되는 메소드(서버전송 후 실행되는 메소드)
            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);

                // resultTextView에 json결과 값을 출력
                resultTextView.setText(jsonObject.toString());
                Log.i(TAG, jsonObject.toString());

            }

        }.execute();

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
