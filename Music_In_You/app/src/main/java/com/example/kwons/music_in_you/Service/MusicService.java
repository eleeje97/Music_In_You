package com.example.kwons.music_in_you.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    final static String MESSAGE_KEY = "MusicPlay"; // 인텐트로 주고받는 재생 flag

    MediaPlayer mediaPlayer;

    public MusicService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean message = intent.getExtras().getBoolean(MusicService.MESSAGE_KEY);

        if(message) {
            //mediaPlayer = MediaPlayer.create(this, 음악 URI);
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
