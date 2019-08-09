package com.example.kwons.music_in_you;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.kwons.music_in_you.MusicDTO;

import java.util.ArrayList;
import java.util.Random;


public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<MusicDTO> list;
    public static MediaPlayer mediaPlayer;
    private TextView title,artist, currentDuration, duration;
    private ImageView album,previous,next;
    private Button list_img,play,pause;
    private SeekBar seekBar;
    boolean isPlaying = true;
    private ContentResolver res;
    private ProgressUpdate progressUpdate;
    private int position;
    private int  count; // 플레이버튼과 정지 버튼 구현
    private int totalTime; // 음악 전체 시간
    private int currentTime; // 음악의 현재 데이터 위치(시간)
    private ToggleButton random_btn;
    private Boolean isRandomed = false; // 랜덤 상태를 저장하기 위해서
    // 액티비티 죽이기 위해서 객체 선언
    // 리스트 페이지로 넘어갔을 때 현재 음악 페이지를 죽이기 위해서 만든것임
    public static MusicPlayActivity musicPlayActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        // intent
        final Intent intent = getIntent();
        //isRandomed = intent.getExtras().getBoolean("isRandomed");
        musicPlayActivity = MusicPlayActivity.this; // 현재 클래스를 담아줌
        mediaPlayer = new MediaPlayer();
        title = (TextView)findViewById(R.id.title);
        artist = (TextView)findViewById(R.id.artist);
        album = (ImageView)findViewById(R.id.album);
        seekBar = (SeekBar)findViewById(R.id.seekbar);

        duration = (TextView)findViewById(R.id.duration); // 음악 파일의 총 길이(시간)
        currentDuration = (TextView)findViewById(R.id.currentDuration); // 현재 데이터 위치
        final ToggleButton likebtn = (ToggleButton)findViewById(R.id.heart); // 좋아요 버튼
        random_btn = (ToggleButton)findViewById(R.id.random); // 랜덤 버튼
        final ToggleButton repeat = (ToggleButton)findViewById(R.id.repeat); // 반복 버튼
        position = intent.getIntExtra("position",0);
        list = (ArrayList<MusicDTO>) intent.getSerializableExtra("playlist");
        res = getContentResolver();

        // 음악 재생 관련 버튼
        previous = (ImageView)findViewById(R.id.pre);
        play = (Button)findViewById(R.id.play);
        pause = (Button)findViewById(R.id.pause);
        next = (ImageView)findViewById(R.id.next);
        list_img = (Button) findViewById(R.id.list);

        // 버튼 리스너 추가
        previous.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        repeat.setOnClickListener(this);

        playMusic(list.get(position));
        progressUpdate = new ProgressUpdate();
        progressUpdate.start();


        // seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                if(seekBar.getProgress()>0 && play.getVisibility()==View.GONE){
                    mediaPlayer.start();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(position+1<list.size()) {
                    position++;
                    playMusic(list.get(position));
                }
            }
        });

        // 좋아요버튼이 눌렸을 때
        likebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (likebtn.isChecked()) {
                    likebtn.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.full_heart,null)
                    );
                    Toast.makeText(getApplicationContext(),"좋아요",Toast.LENGTH_SHORT).show();
                } else {
                    likebtn.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.emty_heart,null)
                    );
                    Toast.makeText(getApplicationContext(),"좋아요 취소",Toast.LENGTH_SHORT).show();
                }
            } // end onClick()


        });



        // 리스트로 돌아가는 버튼이 눌러졌을 때
        list_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class); // 홈의 리스트 목록으로 이동 하도록 해야함☆
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //intent.putExtra("isRandomed",isRandomed);
                intent.putExtra("position", 1);
                startActivity(intent);

            }});


        // 플레이 버튼이 눌러졌을 때
        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 재생 목록을 누르면 재생
                // 정지상태 일 때
                // 플레이 버튼을 누르면 재생
                pause.setVisibility(View.VISIBLE); // 플레이 버튼이 눌리면 정지 버튼이 보여줌
                play.setVisibility(View.GONE);
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                currentDuration.setText(DateFormat.format("mm:ss",mediaPlayer.getCurrentPosition()));
                mediaPlayer.start();

            }});


        // 정지 버튼이 눌러졌을 때
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 정지 버튼
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                mediaPlayer.pause();


            }});

        // 랜덤 버튼이 눌렸을 때
        random_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(random_btn.isChecked()){ // 랜덤 재생일 때
                    isRandomed = true;
                    random_btn.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.random_icon,null)
                    );

                    // 랜덤 기능을 수행하기 위해
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Random rand = new Random();
                            position = rand.nextInt((list.size() - 1) - 0 + 1) + 0;
                            playMusic(list.get(position));
                            seekBar.setProgress(0);
                        }
                    });
                }
                else{
                    random_btn.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.random_checked_icon,null)
                    );

                }
            }

    });

        // 반복 버튼이 눌렀을 때
        repeat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(repeat.isChecked()){ // 반복버튼이 체크되었다면
                    repeat.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.repeat_icon,null)
                    );

                    // 반복 기능을 수행하기 위해 미디어플레이어 객체를 사용
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            //playMusic(list.get(position)); // 한곡반복

                            // 전체 리스트 무한반복
                            if(position == list.size()-1) { // 현재 곡이 마지막 곡이라면
                                // 현재 음악의 길이와 진행되는 음악 길이가 같아지면

                                    position = 0;
                                    playMusic(list.get(position));
                                    seekBar.setProgress(0);
                                }
                            else{ // 마지막 곡이 아니라면 순서대로 재생함
                                position++;
                                playMusic(list.get(position));
                            }
                        }
                    });

                }
                else{ // 반복버튼이 해제 되었다면
                    repeat.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.repeat_checked_icon,null)
                    );
                }

            }
        });

    }



   @Override
    public void onBackPressed(){
        /*super.onBackPressed();
        isPlaying = false;
        Intent intent_music = new Intent(getApplicationContext(),MainActivity_PLAYLIST.class);
        //intent_music.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 쌓여 있는 액티비티 정리
        //intent_music.putExtra("isPlaying", isPlaying);
        startActivity(intent_music);
    */
        return;
    }



    // 음악이 플레이 될 때 호출될 함수
    public void playMusic(MusicDTO musicDto) {

        try {
            seekBar.setProgress(0);
            title.setText(musicDto.getTitle());
            artist.setText(musicDto.getArtist());
            Uri musicURI = Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ""+musicDto.getId()); //경로
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, musicURI);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            currentDuration.setText(DateFormat.format("mm:ss",currentTime)); // 현재 재생되는 노래의 현재 위치(시간)을 나타냄


            if(mediaPlayer.isPlaying()){ // 재생중이라면
                play.setVisibility(View.GONE); // 공간차지하는 것 없이 아이콘을 숨김
                pause.setVisibility(View.VISIBLE); // 보임
                totalTime = (Integer.parseInt(musicDto.getDuration()));
                duration.setText(DateFormat.format("mm:ss",totalTime)); // 현재 재생되는 노래의 전체 길이(시간)을 나타냄


            }

            else{ // 정지상태라면
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            }


            Bitmap bitmap = BitmapFactory.decodeFile(getCoverArtPath(Long.parseLong(musicDto.getAlbumId()),getApplication()));
            album.setImageBitmap(bitmap);

        }
        catch (Exception e) {
            Log.e("SimplePlayer", e.getMessage());
        }
    }

    //앨범이 저장되어 있는 경로를 리턴합니다.
    private static String getCoverArtPath(long albumId, Context context) {

        Cursor albumCursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.pre:
                // 이전 곡으로 돌아가는 버튼
                // 현재 재생 곡 이전으로 돌아감
                //랜덤 버튼이 체크되어있다면, 이전곡도 랜덤으로
                if(random_btn.isChecked()){
                    Random rand = new Random();
                    position = rand.nextInt((list.size() - 1) - 0 + 1) + 0;
                    playMusic(list.get(position));
                    seekBar.setProgress(0);
                }
                else { // 랜덤 버튼이 해제되어있다면 순서대로
                    if (position - 1 >= 0) {
                        position--;
                        playMusic(list.get(position));
                        seekBar.setProgress(0);
                    }

                    else{ // 재생 목록의 처음일 경우 마지막 곡으로 이동
                        position = list.size()-1;
                        playMusic(list.get(position));
                        seekBar.setProgress(0);
                    }
                }

                break;

            case R.id.next:
                // 다음 곡을 재생하는 버튼
                // 현재 재생 곡 다음으로 이동함
                if(random_btn.isChecked()){
                    Random rand = new Random();
                    position = rand.nextInt((list.size() - 1) - 0 + 1) + 0;
                    playMusic(list.get(position));
                    seekBar.setProgress(0);

                }
                else{
                    if(position+1<list.size()){
                        position++;
                        playMusic(list.get(position));
                        seekBar.setProgress(0);
                    }

                    else { // 현재 재생 리스트 목록 보다 크다면 처음으로 돌아감
                        position = 0;
                        playMusic(list.get(position));
                        seekBar.setProgress(0);
                    }
                }


                break;




        }


    }


    class ProgressUpdate extends Thread{
        @Override
        public void run() {
            while(isPlaying){
                try {
                    Thread.sleep(500);
                    if(mediaPlayer!=null){
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        currentDuration.setText(DateFormat.format("mm:ss",mediaPlayer.getCurrentPosition())); // 실시간 시간을 보여줌
                        //pause.setVisibility(View.GONE);
                        //play.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.e("ProgressUpdate",e.getMessage());
                }

    }
}
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
