package com.example.subinapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 데이터베이스 초기화를 위한 변수
    private static final String DBNAME="android.db";
    private static final int DBVERSION=1;


    // 생성자- 클래스 호출시 자동으로 sqlite db 파일 생성 (한번만)
    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    // db 파일 생성시 자동으로 실행하는 메서드
    // 주로 테이블 생성시 사용
    @Override
    public void onCreate(SQLiteDatabase db) {
      String SQL="create table member (" +
              "mno int primary key autoincrement,"+
              "userid varchar(18) unique,"+
              "passwd varchar(18) not null,"+
              "name varchar(18) not null,"+
              "regdate datetime default current_timestamp)";
      db.execSQL(SQL);
    }

    //테이블 재생성시 사용
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists member");
        onCreate(db);
    }
    // 회원 가입 처리
    public boolean insertMember(
            String userid, String passwd, String name, String email){
            // 테이블에 레코드를 저장하기 위해 sqlite 초기화
            SQLiteDatabase db = this.getWritableDatabase();
            // 저장할 데이터를 컨테이너로 생성
        ContentValues values = new ContentValues();
        values.put("userid", userid);
        values.put("passwd", passwd);
        values.put("name", name);
        values.put("email", email);

        //테이블에 데이터 저장
        long result =db.insert("member", null, values);

        // db 연결 해제
        db.close();

        // 저장 성공 여부 리턴
        return result !=-1;
    }

    // 아이디 중복 확인
}