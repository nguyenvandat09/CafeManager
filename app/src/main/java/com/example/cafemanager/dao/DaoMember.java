package com.example.cafemanager.dao;



import static com.example.cafemanager.database.MySqlHeper.KEY_THANHVIEN_HOTEN;
import static com.example.cafemanager.database.MySqlHeper.KEY_THANHVIEN_MAKVIP;

import static com.example.cafemanager.database.MySqlHeper.KEY_THANHVIEN_SDT;
import static com.example.cafemanager.database.MySqlHeper.KEY_THANHVIEN_SoPhanTramGiam;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_KVIP;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.Member;

import java.util.ArrayList;
import java.util.List;

public class DaoMember {
    MySqlHeper mMySqlHeper;
    SQLiteDatabase mSQLiteDatabase;
    public DaoMember(Context context){
        mMySqlHeper = new MySqlHeper(context);
    }

    public List<Member> getListMember(){
        List<Member> list = new ArrayList<Member>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_KVIP ;

        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        Member user ;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(0));
                String tenMember = cursor.getString(1);
                String SDT = cursor.getString(2);
                int soPhanTramGiam = Integer.parseInt(cursor.getString(3));
                user = new Member(id , tenMember , SDT,soPhanTramGiam );
                list.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return list;
    }
    public boolean deleteTitle(int id) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        return mSQLiteDatabase.delete(TABLE_NAME_KVIP, KEY_THANHVIEN_MAKVIP + "=?", new String[]{String.valueOf(id)}) > 0;
    }
    public  boolean themKind(Member mode){
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_THANHVIEN_HOTEN , mode.getName());
        values.put(KEY_THANHVIEN_SDT , mode.getSDT());
        values.put(KEY_THANHVIEN_SoPhanTramGiam , mode.getSoPhanTramGiam());
        long result = this.mSQLiteDatabase.insert(TABLE_NAME_KVIP , null, values);
        if (result <= 0) {
            return false;
        }

        return true;
    }
    public boolean editKind(Member mode) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_THANHVIEN_HOTEN , mode.getName());
        values.put(KEY_THANHVIEN_SDT , mode.getSDT());
        values.put(KEY_THANHVIEN_SoPhanTramGiam , mode.getSoPhanTramGiam());
        int r = this.mSQLiteDatabase.update(TABLE_NAME_KVIP, values, KEY_THANHVIEN_MAKVIP+"=?", new String[]{String.valueOf(mode.getId())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
    public List<Member> getListSearch(String tenSachSearch ) {
        List<Member> list = new ArrayList<Member>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT " + TABLE_NAME_KVIP + "." +KEY_THANHVIEN_MAKVIP +
                " , " + KEY_THANHVIEN_HOTEN +
                " , " + KEY_THANHVIEN_SDT +
                " , " + KEY_THANHVIEN_SoPhanTramGiam +
                " FROM " + TABLE_NAME_KVIP +
                " WHERE " + TABLE_NAME_KVIP + "." +KEY_THANHVIEN_HOTEN + " LIKE '%" +tenSachSearch + "%' OR " +KEY_THANHVIEN_HOTEN  +" = " + "'  "+tenSachSearch+ "'  ";
        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        Member user ;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(0));
                String tenLoaiSach = cursor.getString(1);
                String SDT = cursor.getString(2);
                int soPhanTramGiam = Integer.parseInt(cursor.getString(3));
                user = new Member(id , tenLoaiSach,SDT,soPhanTramGiam);
                list.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return list;
    }
}
