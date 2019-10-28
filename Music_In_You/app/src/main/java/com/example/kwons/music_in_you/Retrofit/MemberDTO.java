package com.example.kwons.music_in_you.Retrofit;

// 회원가입한 사람들의 정보를 저장할 DTO
public class MemberDTO {
    String email;
    String name;
    String password1;
    String password2;
    String date_of_birth;

    // 감정별 음악 취향을 저장하고 있는 객체
    MusicPreference music_prefernce;

    // 모든 인자를 매개변수로 가지는 생성자를 만듬
    public MemberDTO(String email, String name, String password1, String password2, String date_of_birth, MusicPreference music_prefernce){
        this.email = email;
        this.name = name;
        this.password1 = password1;
        this.password2 = password2;
        this.date_of_birth = date_of_birth;
        this.music_prefernce = music_prefernce;
    }

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

    public MusicPreference getMusic_prefernce() {
        return music_prefernce;
    }

    public void setMusic_prefernce(MusicPreference music_prefernce) {
        this.music_prefernce = music_prefernce;
    }
}
