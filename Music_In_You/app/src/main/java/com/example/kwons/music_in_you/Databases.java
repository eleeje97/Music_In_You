package com.example.kwons.music_in_you;

import android.provider.BaseColumns;

public final class Databases {

    public static final class CreateDB implements BaseColumns {
        public static final String IDX = "idx";
        public static final String SONGID = "song_id";
        public static final String HAPPY = "happy";
        public static final String SAD = "sad";
        public static final String AGGRESSIVE = "aggressive";
        public static final String RELAXED = "relaxed";
        public static final String LOVE = "love";
        public static final String COUNT = "count";
        public static final String _TABLENAME0 = "songs";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + IDX +" integer primary key autoincrement, "
                + SONGID + " text not null, "
                + HAPPY + " real, "
                + SAD + " real, "
                + AGGRESSIVE + " real, "
                + RELAXED + " real, "
                + LOVE + " integer not null, "
                + COUNT + " integer not null);";
    }

    public static final class DeleteDB {
        public static final String _DELETE0 = "DROP TABLE IF EXISTS songs";
    }
}