package com.example.kwons.music_in_you.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

import com.example.kwons.music_in_you.MusicDTO;

public class MusicService extends Service {
    public final static String MESSAGE_KEY = "MusicPlay"; // 인텐트로 주고받는 재생 flag
    public final static String MUSIC_DTO = "MusicDTO";// 재생할 음악의 DTO 객체를 받을 인텐트 flag
    MediaPlayer mediaPlayer;
    Uri musicURI; // 재생할 음악의 URI

    public MusicService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean message = intent.getExtras().getBoolean(MusicService.MESSAGE_KEY);
        MusicDTO musicDTO = (MusicDTO) intent.getExtras().get(MusicService.MUSIC_DTO);

        musicURI = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicDTO.getId());

        if(message) {
            mediaPlayer = MediaPlayer.create(this, musicURI);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
