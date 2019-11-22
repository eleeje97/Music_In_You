package com.example.kwons.music_in_you.Retrofit;

import com.google.gson.annotations.SerializedName;

public class MemberResult {

    //@SerializedName("email")
    private String email;

    //@SerializedName("name")
    private String name;

    //@SerializedName("password1")
    private String password1;

    //@SerializedName("password2")
    private String password2;

    //@SerializedName("date_of_birth")
    private String date_of_birth;

    // 감정별 음악 취향을 저장하고 있는 객체
    //@SerializedName("music_preference")
    MusicPreference music_preference;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public MusicPreference getMusic_preference() {
        return music_preference;
    }

    public void setMusic_preference(MusicPreference music_preference) {
        this.music_preference = music_preference;
    }
}
