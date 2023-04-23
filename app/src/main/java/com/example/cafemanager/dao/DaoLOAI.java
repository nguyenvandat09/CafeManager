package com.example.cafemanager.dao;




import static com.example.cafemanager.database.MySqlHeper.KEY_LOAI_MALOAI;
import static com.example.cafemanager.database.MySqlHeper.KEY_LOAI_TENLOAI;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_LOAI;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.Kindof;

import java.util.ArrayList;
import java.util.List;

public class DaoLOAI {
    MySqlHeper mMySqlHeper;
    SQLiteDatabase mSQLiteDatabase;
    public DaoLOAI(Context context){
        mMySqlHeper = new MySqlHeper(context);
    }


    public List<Kindof> getListOfKindofBooks(){
        List<Kindof> lisst = new ArrayList<Kindof>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_LOAI;

        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        Kindof user ;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                user = new Kindof(id , name);
                lisst.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return lisst;
    }
    public boolean deleteTitle(int id) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        return mSQLiteDatabase.delete(TABLE_NAME_LOAI, KEY_LOAI_MALOAI + "=?", new String[]{String.valueOf(id)}) > 0;
    }
    public  boolean themKind(Kindof mode){
               this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
              ContentValues values = new ContentValues();
              values.put(KEY_LOAI_TENLOAI , mode.getTenloaiSach() );
              long result = this.mSQLiteDatabase.insert(TABLE_NAME_LOAI , null, values);
              if (result <= 0) {
                  return false;
              }
              return true;
          }
    public boolean editKind(Kindof mode) {
               this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
               ContentValues values = new ContentValues();
               values.put(KEY_LOAI_TENLOAI , mode.getTenloaiSach() );
                   int r = this.mSQLiteDatabase.update(TABLE_NAME_LOAI, values, KEY_LOAI_MALOAI+"=?", new String[]{String.valueOf(mode.getIdLoaiSach())});
                   if (r <= 0) {
                       return false;
                   }
                   return true;
               }
    public List<String> getListNameKindOfBook(){
                    List<String> result = new ArrayList<String>();
                   this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
                   String sql = "SELECT * FROM " + TABLE_NAME_LOAI;
                   Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
                   if (cursor.moveToFirst()) {
                       while (!cursor.isAfterLast()) {
                           String name = cursor.getString(1);
                           result.add(name);
                           cursor.moveToNext();
                       }
                   }
                   cursor.close();
                   this.mSQLiteDatabase.close();


                    return result;
               }
    public int getIdLoaiSach(String tenLoai){
                    int id = 0;
                   this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
                   String sql = "SELECT " + TABLE_NAME_LOAI + "." +KEY_LOAI_MALOAI+
                           " AS idLoaiSach FROM " + TABLE_NAME_LOAI +
                           " WHERE " + TABLE_NAME_LOAI+ "."
                           + KEY_LOAI_TENLOAI + " ='" + tenLoai+"'"  ;
                   Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
                   if (cursor.moveToFirst()) {
                       while (!cursor.isAfterLast()) {
                           id = Integer.parseInt(cursor.getString(0));
                           cursor.moveToNext();
                       }
                   }
                   cursor.close();
                   this.mSQLiteDatabase.close();

                    return id;
               }
}
