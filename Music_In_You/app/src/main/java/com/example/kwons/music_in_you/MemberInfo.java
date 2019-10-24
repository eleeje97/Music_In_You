package com.example.kwons.music_in_you;

public class MemberInfo {

    // 회원 정보 변수
    String email;
    String name;
    String password;
    String date_of_birth;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
