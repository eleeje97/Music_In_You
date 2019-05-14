package com.example.kwons.music_in_you;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionCheck {
    public static final int MY_PERMISSION_RECORD = 1;
    //public static final int MY_PERMISSION_STORAGE = 2;
    Context mContext;

    public PermissionCheck(Context context) {
        mContext = context;
    }

    public boolean isChecked(String permission) {
        switch(permission) {
            case "Record":
                Log.i("Record Permission", "CALL");

                // RECORD 권한이 부여되어 있지 않다면
                if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    // 다시 보지 않기 버튼을 만드려면 else문 대신 이 부분을 사용
                    //ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_RECORD);

                    if(ActivityCompat.shouldShowRequestPermissionRationale((Activity)mContext, Manifest.permission.RECORD_AUDIO)) {
                        new AlertDialog.Builder(mContext).setTitle("알림").setMessage("마이크 접근 권한을 허용해주세요.").setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:com.example.kwons.music_in_you"));
                                mContext.startActivity(intent);

                            }
                        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity)mContext).finish();
                            }
                        }).setCancelable(false).create().show();
                    } else {
                        ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSION_RECORD);
                    }
                } else {
                    return true;
                }

                break;


            default:
                return false;
        }
        return false;
    }
}
