package com.example.kwons.music_in_you;

import android.provider.BaseColumns;

public final class Databases {

    public static final class CreateDB implements BaseColumns {
        public static final String SONGURI = "song_uri";
        public static final String HAPPY = "happy";
        public static final String SAD = "sad";
        public static final String AGGRESSIVE = "aggressive";
        public static final String RELAXED = "relaxed";
        public static final String LIKE = "like";
        public static final String _TABLENAME0 = "songs";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + SONGURI + " text not null, "
                + HAPPY + " real not null, "
                + SAD + " real not null, "
                + AGGRESSIVE + " real not null, "
                + RELAXED + " real not null, "
                + LIKE + " integer not null);";
    }
}