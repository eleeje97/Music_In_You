package com.example.kwons.music_in_you;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    // 다른 클래스를 상속받아서 사용자만의 기능 확장형 클래스를 만들 때, 생성자가 필요함
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment_home home_tab = new TabFragment_home();
                return home_tab;
            case 1:
                TabFragment_playlist playlist_tab = new TabFragment_playlist();
                return playlist_tab;
            case 2:
                TabFragment_setting setting_tab = new TabFragment_setting();
                return setting_tab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
