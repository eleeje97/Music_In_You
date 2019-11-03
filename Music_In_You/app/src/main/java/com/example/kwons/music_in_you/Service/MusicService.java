package com.example.kwons.music_in_you.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

import com.example.kwons.music_in_you.MusicDTO;

import java.util.ArrayList;

public class MusicService extends Service {
    public final static String MUSIC_DTO = "MusicDTO"; // 재생할 음악의 DTO 객체를 받을 인텐트 flag
    public final static String MESSAGE_KEY = "MessageKey"; // 재생,일시정지,정지 flag
    public final static String POSITION = "Position";
    public final static String MUSIC_LIST = "MusicList";

    // intent 값들
    public final static int MUSIC_PLAY = 0; // 재생 flag
    public final static int MUSIC_PAUSE = 1; // 일시정지 flag
    public final static int MUSIC_STOP = 2; // 정지 flag

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    Uri musicURI; // 재생할 음악의 URI

    int position;
    ArrayList<MusicDTO> list;

    public MusicService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int message = intent.getExtras().getInt(MusicService.MESSAGE_KEY);
        MusicDTO musicDTO = (MusicDTO) intent.getExtras().get(MusicService.MUSIC_DTO);
        position = intent.getExtras().getInt(MusicService.POSITION);
        list = (ArrayList<MusicDTO>)intent.getExtras().getSerializable(MusicService.MUSIC_LIST);

        musicURI = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicDTO.getId());

        /**
        switch (message) {
            case MUSIC_PLAY:
                mediaPlayer = MediaPlayer.create(this, musicURI);
                mediaPlayer.start();
                break;
            case MUSIC_PAUSE:
                mediaPlayer.pause();
                break;
            case MUSIC_STOP:
                mediaPlayer.stop();
                mediaPlayer.release();
                break;
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(position + 1 < list.size()) {
                    position++;
                    playMusic(list.get(position));
                }
            }
        });
         **/

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
