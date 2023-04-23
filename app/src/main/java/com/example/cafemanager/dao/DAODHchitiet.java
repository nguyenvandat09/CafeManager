package com.example.cafemanager.dao;


import static com.example.cafemanager.database.MySqlHeper.KEY_CHITIET_GIA;
import static com.example.cafemanager.database.MySqlHeper.KEY_CHITIET_MA;
import static com.example.cafemanager.database.MySqlHeper.KEY_CHITIET_MAHOADON;
import static com.example.cafemanager.database.MySqlHeper.KEY_CHITIET_MASP;
import static com.example.cafemanager.database.MySqlHeper.KEY_CHITIET_SOLUONG;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_HOADONCHITIET;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.HDCT;

import java.util.ArrayList;
import java.util.List;

public class DAODHchitiet {
    MySqlHeper mMySqlHeper;
    SQLiteDatabase mSQLiteDatabase;



    public DAODHchitiet(Context context) {
        mMySqlHeper = new MySqlHeper(context);
    }
    public List<HDCT> getByid(String id){
        String sql=" SELECT * FROM HOADONCHITIET WHERE MAHOADON =? ";
        return getListhoadon(sql,id);
    }

    @SuppressLint("Range")
    public List<HDCT> getListhoadon(String sql, String...SelectArgs){
        List<HDCT> list = new ArrayList<HDCT>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, SelectArgs);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HDCT user;
                int id = Integer.parseInt(cursor.getString(0));
                int mahd = Integer.parseInt(cursor.getString(1));
                String masp = cursor.getString(2);
                int soluong = Integer.parseInt(cursor.getString(3));
                float gia= Float.parseFloat(cursor.getString(4));
                user = new HDCT( id , mahd , masp  ,soluong,gia);
                list.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return list;
    }
    public boolean deletecong(int id) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        return mSQLiteDatabase.delete(TABLE_NAME_HOADONCHITIET, KEY_CHITIET_MA+ "=?", new String[]{String.valueOf(id)}) > 0;
    }
    public boolean themcong(HDCT thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CHITIET_MAHOADON, thuthu.getHoaDon());
        values.put(KEY_CHITIET_MASP, thuthu.getNamesp());
        values.put(KEY_CHITIET_SOLUONG, thuthu.getSoLuongMua());
        values.put(KEY_CHITIET_GIA, thuthu.getGia());

        long result = this.mSQLiteDatabase.insert(TABLE_NAME_HOADONCHITIET, null, values);
        if (result <= 0) {
            return false;
        }
        return true;
    }

    }
