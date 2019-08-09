package com.example.kwons.music_in_you;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    List<MusicDTO> list;
    LayoutInflater inflater;
    Activity activity;


    public MyAdapter() {
    }

    public MyAdapter(Activity activity, List<MusicDTO> list) {
        this.list = list;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview, parent, false); // 아이템의 데이터를 보여주기 위해 inflate
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            convertView.setLayoutParams(layoutParams);

        }


        ImageView imageView = (ImageView) convertView.findViewById(R.id.album);


        Bitmap albumImage = getAlbumImage(activity, Integer.parseInt((list.get(position)).getAlbumId()), 170);

        imageView.setImageBitmap(albumImage);


        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setTextColor(Color.WHITE); // 글씨 색 변경
        title.setText(list.get(position).getTitle());

        TextView artist = (TextView) convertView.findViewById(R.id.artist);
        artist.setTextColor(Color.WHITE);
        artist.setText(list.get(position).getArtist());

        return convertView;
    }

    // 비트맵 옵션을 설정하기 위해서 사용함
    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    // 앨범 이미지의 크기를 조절하기 위해서
    private static Bitmap getAlbumImage(Context context, int album_id, int MAX_IMAGE_SIZE) {

        ContentResolver res = context.getContentResolver();
        Uri uri = Uri.parse(("content://media/external/audio/albumart/"+ album_id)); // 경로"content://media/external/audio/media/"

        // URI가 있다면
        if (uri != null) {
            ParcelFileDescriptor fd = null; // 열려있는 프로세스를 읽고 쓰고 네트워크 소캣을 열 때 사용하는 객체임
            try {

                fd = res.openFileDescriptor(uri, "r");

                //크기를 얻어오기 위한옵션 ,
                //inJustDecodeBounds값이 true로 설정되면 decoder가 bitmap object에 대해 메모리를 할당하지 않고, 따라서 bitmap을 반환하지도 않는다.
                // 다만 options fields는 값이 채워지기 때문에 Load 하려는 이미지의 크기를 포함한 정보들을 얻어올 수 있다.
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, options);

                int scale = 0;

                if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
                    scale = (int) Math.pow(2, (int) Math.round(Math.log(MAX_IMAGE_SIZE / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;



                Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, options);

                if (b != null) {

                    // finally rescale to exactly the size we need
                    if (options.outWidth != MAX_IMAGE_SIZE || options.outHeight != MAX_IMAGE_SIZE) {

                        Bitmap tmp = Bitmap.createScaledBitmap(b, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, true);
                        b.recycle();
                        b = tmp;
                    }
                }

                return b;
            }
            catch (FileNotFoundException e) {
                //Log.e("이미지 에러","출력못함");
                //Log.e("이미지에러 메세지 : ",e.getMessage());
            }
            finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }


}

