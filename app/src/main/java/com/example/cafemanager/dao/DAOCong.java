package com.example.cafemanager.dao;

import static com.example.cafemanager.database.MySqlHeper.KEY_CONG_LYDONGHI;
import static com.example.cafemanager.database.MySqlHeper.KEY_CONG_MACONG;
import static com.example.cafemanager.database.MySqlHeper.KEY_CONG_MANV;
import static com.example.cafemanager.database.MySqlHeper.KEY_CONG_NGAYNGHI;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_CONG;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.Cong;
import java.util.ArrayList;
import java.util.List;

public class DAOCong {

    MySqlHeper mMySqlHeper;
    SQLiteDatabase mSQLiteDatabase;



    public DAOCong(Context context) {
       mMySqlHeper = new MySqlHeper(context);
    }
    public List<Cong> getByid(String id){
        String sql="SELECT *FROM CONG WHERE MANV =?";
        return getListCong(sql,id);
    }

    @SuppressLint("Range")
    public List<Cong> getListCong(String sql, String...SelectArgs){
        List<Cong> list = new ArrayList<Cong>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
       // String sql = "SELECT * FROM " + TABLE_NAME_NHANVIEN;

        Cursor cursor = mSQLiteDatabase.rawQuery(sql, SelectArgs);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Cong user;
                int id = Integer.parseInt(cursor.getString(0));
                int manv = Integer.parseInt(cursor.getString(1));
                String ngayghi =String.valueOf(cursor.getString(2)) ;
                String lydonghi = String.valueOf(cursor.getString(3));


                user = new Cong( id , manv , ngayghi  ,lydonghi);
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
        return mSQLiteDatabase.delete(TABLE_NAME_CONG, KEY_CONG_MACONG+ "=?", new String[]{String.valueOf(id)}) > 0;
    }
    public boolean themcong(Cong thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONG_MANV, thuthu.getIdnv());
        values.put(KEY_CONG_NGAYNGHI, thuthu.getNgaynghi());
        values.put(KEY_CONG_LYDONGHI, thuthu.getLyDo());

        long result = this.mSQLiteDatabase.insert(TABLE_NAME_CONG, null, values);
        if (result <= 0) {
            return false;
        }
        return true;
    }

    public boolean editKind(Cong thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONG_MANV, thuthu.getIdnv());
        values.put(KEY_CONG_NGAYNGHI, thuthu.getNgaynghi());
        values.put(KEY_CONG_LYDONGHI, thuthu.getLyDo());
        int r = this.mSQLiteDatabase.update(TABLE_NAME_CONG, values, KEY_CONG_MACONG + "=?", new String[]{String.valueOf(thuthu.getId())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
}
