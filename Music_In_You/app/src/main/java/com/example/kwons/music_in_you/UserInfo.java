package com.example.kwons.music_in_you;

// 사용자가 회원가입 할 때 입력한 정보를 저장하는 클래스
// 서버로 올릴 클래스

public class UserInfo {
    String email;
    String password;
    String name;
    int birthday;

    int normal;
    int happy;
    int sad;
    int angry;
    int calm;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getSad() {
        return sad;
    }

    public void setSad(int sad) {
        this.sad = sad;
    }

    public int getAngry() {
        return angry;
    }

    public void setAngry(int angry) {
        this.angry = angry;
    }

    public int getCalm() {
        return calm;
    }

    public void setCalm(int calm) {
        this.calm = calm;
    }
}
