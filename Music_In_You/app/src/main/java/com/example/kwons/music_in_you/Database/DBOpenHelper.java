package com.example.kwons.music_in_you.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // DatabaseHelper 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(Databases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+Databases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }



    // DBOpenHelper 생성자
    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    // 데이터베이스를 열어서 사용할 수 있도록 하는 함수
    public DBOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    // 데이터베이스에 테이블 생성
    public void create(){
        mDBHelper.onCreate(mDB);
    }

    // 데이터베이스를 닫는다
    public void close(){
        mDB.close();
    }

    // 데이터 삽입 - emotion 컬럼 제외
    public long insertColumn(int idx, String song_id, double happy, double sad, double aggressive, double relaxed, int love, int count) {
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.IDX, idx); // 음악의 인덱스 저장
        values.put(Databases.CreateDB.SONGID, song_id); // 음악 경로
        values.put(Databases.CreateDB.HAPPY, happy); // happy 감정 가중치
        values.put(Databases.CreateDB.SAD, sad); // sad 감정 가중치
        values.put(Databases.CreateDB.AGGRESSIVE, aggressive); // aggressive 감정 가중치
        values.put(Databases.CreateDB.RELAXED, relaxed); // relaxed 감정 가중치
        values.put(Databases.CreateDB.LOVE, love); // 좋아요 여부
        values.put(Databases.CreateDB.COUNT, count); // 재생된 횟수

        return mDB.insert(Databases.CreateDB._TABLENAME0, null, values);
    }

    // 데이터 조회
    public Cursor selectColumns(){
        return mDB.query(Databases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    // 특정 데이터 조회
    public Cursor selectColumns(String song_id){
        return mDB.rawQuery( "SELECT * FROM songs WHERE song_id=" + song_id + ";", null);
    }

    // 정렬하여 조회
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM songs ORDER BY " + sort + ";", null);
        return c;
    }

    // LOVE가 1인 데이터 조회
    public Cursor selectLoveSongs() {
        return mDB.rawQuery( "SELECT idx FROM songs WHERE love=1;", null);
    }

    // 특정 데이터의 LOVE 컬럼 조회
    public int selectLoveColumn(String song_id) {
        int like = 0;
        Cursor c = mDB.rawQuery( "SELECT love FROM songs WHERE song_id=" + song_id + ";", null);
        while(c.moveToNext()){
            like = c.getInt(c.getColumnIndex("love"));
        }

        return like;
    }

    // 많이 재생한 곡 조회
    public Cursor selectFrequentSongs() {
        Cursor c = mDB.rawQuery( "SELECT * FROM songs ORDER BY count DESC LIMIT 20;", null);
        return c;
    }

    // 감정별 곡 조회
    public Cursor selectSongsByEmotion(String emotion) {
        Cursor c = mDB.rawQuery( "SELECT * FROM songs WHERE emotion='" + emotion + "';", null);
        return c;
    }

    // 데이터 갱신
    public boolean updateColumn(String song_id, double happy, double sad, double aggressive, double relaxed, int love, int count){
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.HAPPY, happy);
        values.put(Databases.CreateDB.SAD, sad);
        values.put(Databases.CreateDB.AGGRESSIVE, aggressive);
        values.put(Databases.CreateDB.RELAXED, relaxed);
        values.put(Databases.CreateDB.LOVE, love);
        values.put(Databases.CreateDB.COUNT, count);
        return mDB.update(Databases.CreateDB._TABLENAME0, values, "song_id=" + song_id, null) > 0;
    }

    // EMOTION 컬럼 갱신
    public boolean updateEmotionColumn(String song_id, String emotion){
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.EMOTION, emotion);
        return mDB.update(Databases.CreateDB._TABLENAME0, values, "song_id=" + song_id, null) > 0;
    }

    // LOVE 컬럼 갱신
    public boolean updateLoveColumn(String song_id, int love){
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.LOVE, love);
        return mDB.update(Databases.CreateDB._TABLENAME0, values, "song_id=" + song_id, null) > 0;
    }

    // COUNT 컬럼 갱신
    public boolean updateCountColumn(String song_id){
        int count = 0;
        Cursor c = mDB.rawQuery( "SELECT count FROM songs WHERE song_id=" + song_id + ";", null);
        while(c.moveToNext()){
            count = c.getInt(c.getColumnIndex("count"));
        }

        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.COUNT, ++count);
        return mDB.update(Databases.CreateDB._TABLENAME0, values, "song_id=" + song_id, null) > 0;
    }

    // 모든 데이터 삭제
    public void deleteAllColumns() {
        mDB.delete(Databases.CreateDB._TABLENAME0, null, null);
    }

    // 특정 데이터 삭제
    public boolean deleteColumn(String song_id){
        return mDB.delete(Databases.CreateDB._TABLENAME0, "song_id="+song_id, null) > 0;
    }


    // 테이블 삭제
    public void deleteTable() {
        mDB.execSQL(Databases.DeleteDB._DELETE0);
    }

}