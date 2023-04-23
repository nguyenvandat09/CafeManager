package com.example.cafemanager.dao;

import static com.example.cafemanager.database.MySqlHeper.KEY_HOADON_KVIP;
import static com.example.cafemanager.database.MySqlHeper.KEY_HOADON_MA;
import static com.example.cafemanager.database.MySqlHeper.KEY_HOADON_MANV;
import static com.example.cafemanager.database.MySqlHeper.KEY_HOADON_SOBAN;
import static com.example.cafemanager.database.MySqlHeper.KEY_HOADON_THOIGIAN;
import static com.example.cafemanager.database.MySqlHeper.KEY_SACH_MASP;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_HOADON;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_MON;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.HoaDon;
import com.example.cafemanager.model.Mon;


import java.util.ArrayList;
import java.util.List;

public class DAOdonhang1 {
    MySqlHeper mMySqlHeper;
    SQLiteDatabase mSQLiteDatabase;

    public DAOdonhang1(Context context) {
        mMySqlHeper = new MySqlHeper(context);
    }

    @SuppressLint("Range")
    public List<HoaDon> getListHoaDon() {
        List<HoaDon> list = new ArrayList<HoaDon>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = " SELECT * FROM " + TABLE_NAME_HOADON;

        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        HoaDon user;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_HOADON_MA)));
                int manhanvien = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_HOADON_MANV)));
                int makvip = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_HOADON_KVIP)));
                String thoigian = cursor.getString(cursor.getColumnIndex(KEY_HOADON_THOIGIAN));
                int soban = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_HOADON_SOBAN)));
                user = new HoaDon(id , manhanvien , makvip  ,thoigian,soban);
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
        return mSQLiteDatabase.delete(TABLE_NAME_HOADON, KEY_HOADON_MA + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean themKind(HoaDon thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HOADON_MA, thuthu.getMaHoaDon());
        values.put(KEY_HOADON_MANV, thuthu.getManv());
        values.put(KEY_HOADON_KVIP, thuthu.getMakvip());
        values.put(KEY_HOADON_THOIGIAN, thuthu.getNgayMua());
        values.put(KEY_HOADON_SOBAN, thuthu.getSoban());
        long result = this.mSQLiteDatabase.insert(TABLE_NAME_HOADON, null, values);
        if (result <= 0) {
            return false;
        }
        return true;
    }

    public boolean editKind(HoaDon thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_HOADON_MANV, thuthu.getManv());
        values.put(KEY_HOADON_KVIP, thuthu.getMakvip());
        values.put(KEY_HOADON_THOIGIAN, thuthu.getNgayMua());
        values.put(KEY_HOADON_SOBAN, thuthu.getSoban());
        int r = this.mSQLiteDatabase.update(TABLE_NAME_HOADON, values, KEY_HOADON_MA + "=?", new String[]{String.valueOf(thuthu.getMaHoaDon())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
    public HoaDon getMonbyID(int id) {
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = " SELECT * FROM " + TABLE_NAME_HOADON
                + " WHERE " + KEY_HOADON_SOBAN + " = " + id;
        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        HoaDon user = new HoaDon();
        if (cursor.moveToFirst()) {
            user.setMaHoaDon(Integer.parseInt(cursor.getString(0)));


        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return user;
    }
}
