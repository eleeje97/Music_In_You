package com.example.kwons.music_in_you;

import android.support.annotation.NonNull;

import java.io.Serializable;

// 조회한 음악파일의 정보를 담을 클래스
public class MusicDTO implements Serializable , Comparable<MusicDTO> {
    private String id;
    private String albumId;
    private String title;
    private String artist;
    private String duration;
    private String data;
    private String date_added;

    public MusicDTO(){ // 기본 생성자

    }

    public MusicDTO(String id, String albumId, String title, String artist, String duration, String data, String date_added){
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.data = data;
        this.date_added = date_added;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {this.duration = duration; }

    public String getData() {
        return data;
    }

    public void setData(String data) {this.data= data; }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String data) {this.date_added= date_added; }

    @Override
    public String toString() {
        return "MusicDTO{" +
                "id='" + id + '\'' +
                ", albumId='" + albumId + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                ", data='" + data + '\'' +
                ", date_added='" + date_added + '\'' +
                '}';
    }


    // 정렬을 위한 메소드
    @Override
    public int compareTo(@NonNull MusicDTO o) {
        return this.title.compareTo(o.title);
    }
}
