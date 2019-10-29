package com.example.kwons.music_in_you;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<PlaylistItem> playlist_list;
    private int layout; // 곡 목록을 보여줄 레이아웃

    // PlayAdapter 생성자
    public PlaylistAdapter(Context context, int layout, ArrayList<PlaylistItem> playlist_list) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.playlist_list = playlist_list;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return playlist_list.size();
    }

    @Override
    public String getItem(int position) {
        return playlist_list.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        PlaylistItem playlistItem = playlist_list.get(position);

        TextView name = convertView.findViewById(R.id.playlist_name);
        name.setText(playlistItem.getName());


        return convertView;
    }
}
