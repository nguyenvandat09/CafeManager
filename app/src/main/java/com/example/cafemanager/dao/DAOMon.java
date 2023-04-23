package com.example.cafemanager.dao;

import static com.example.cafemanager.database.MySqlHeper.KEY_LOAI_MALOAI;
import static com.example.cafemanager.database.MySqlHeper.KEY_LOAI_TENLOAI;
import static com.example.cafemanager.database.MySqlHeper.KEY_SACH_ANH;
import static com.example.cafemanager.database.MySqlHeper.KEY_SACH_GIA;

import static com.example.cafemanager.database.MySqlHeper.KEY_SACH_LOAI_MALOAI;
import static com.example.cafemanager.database.MySqlHeper.KEY_SACH_MASP;
import static com.example.cafemanager.database.MySqlHeper.KEY_SACH_TENSP;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_LOAI;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_MON;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.Mon;


import java.util.ArrayList;
import java.util.List;

public class DAOMon {
    MySqlHeper mMySqlHeper;
     SQLiteDatabase mSQLiteDatabase;
    public DAOMon(Context context){
        mMySqlHeper = new MySqlHeper(context);
    }

    public List<Mon> getBookList(){
        List<Mon> list = new ArrayList<>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT " + TABLE_NAME_MON + "." +KEY_SACH_MASP +
                "," + TABLE_NAME_LOAI + "." + KEY_LOAI_TENLOAI + " AS TENLOAISACH" +
                " , " + KEY_SACH_TENSP +
                " , " + KEY_SACH_GIA  +
                ", "  + KEY_SACH_ANH +
                ", " + KEY_SACH_LOAI_MALOAI + " FROM " + TABLE_NAME_MON + " JOIN " + TABLE_NAME_LOAI + " ON " +TABLE_NAME_LOAI + "." + KEY_LOAI_MALOAI +" = " + TABLE_NAME_MON + "." + KEY_SACH_LOAI_MALOAI;

        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        Mon user ;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(0));
                String tenLoaiSach = cursor.getString(1);
                String tenSach = cursor.getString(2);
                float giaThue = Float.parseFloat(cursor.getString(3));
                byte[] imgByte =cursor.getBlob(4);
                int idLoaiSach = Integer.parseInt(cursor.getString(5));
                user = new Mon(id , tenSach , tenLoaiSach , giaThue , imgByte ,idLoaiSach);
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
        return mSQLiteDatabase.delete(TABLE_NAME_MON, KEY_SACH_MASP + "=?", new String[]{String.valueOf(id)}) > 0;
    }
    public  boolean themBook(Mon mon){
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SACH_TENSP , mon.getTenSach() );
        values.put(KEY_SACH_GIA , mon.getMoney() );
        values.put(KEY_SACH_ANH , mon.gettPhoto() );
        values.put(KEY_SACH_LOAI_MALOAI, mon.getIdLoaiSach() );
        long result = this.mSQLiteDatabase.insert(TABLE_NAME_MON , null, values);
        if (result <= 0) {
            return false;
        }
        return true;
    }
    public boolean editBoock(Mon mon) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SACH_TENSP , mon.getTenSach() );
        values.put(KEY_SACH_GIA , mon.getMoney() );
        values.put(KEY_SACH_ANH , mon.gettPhoto() );
        values.put(KEY_SACH_LOAI_MALOAI, mon.getIdLoaiSach() );
        int r = this.mSQLiteDatabase.update(TABLE_NAME_MON, values, KEY_SACH_MASP+"=?", new String[]{String.valueOf(mon.getId())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
    public List<Mon> getListSearch(String tenSachSearch ) {
        List<Mon> list = new ArrayList<>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT " + TABLE_NAME_MON + "." +KEY_SACH_MASP + "," + TABLE_NAME_LOAI + "." + KEY_LOAI_TENLOAI + " AS TENLOAISACH" + " , " + KEY_SACH_TENSP + " , " + KEY_SACH_GIA  + ", "  + KEY_SACH_ANH + ", " + KEY_SACH_LOAI_MALOAI+ " FROM " + TABLE_NAME_MON + " JOIN " + TABLE_NAME_LOAI + " ON " +TABLE_NAME_LOAI + "." + KEY_LOAI_MALOAI +" = " + TABLE_NAME_MON + "." + KEY_SACH_LOAI_MALOAI + " WHERE "+TABLE_NAME_MON + "." + KEY_SACH_TENSP + " LIKE '%" +tenSachSearch+ "%' OR TENLOAISACH LIKE '%" +tenSachSearch+ "%'";
        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        Mon user ;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(0));
                String tenLoaiSach = cursor.getString(1);
                String tenSach = cursor.getString(2);
                float giaThue = Float.parseFloat(cursor.getString(3));
                byte[] imgByte =cursor.getBlob(4);
                int idLoaiSach = Integer.parseInt(cursor.getString(5));
                user = new Mon(id , tenSach , tenLoaiSach , giaThue , imgByte,idLoaiSach);
                list.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return list;
    }
//    public static final String TABLE_NAME_MON = "MON";
//    public static final String KEY_SACH_MASP = "MASP";
//    public static final String KEY_SACH_TENSP = "TENSP";
//    public static final String KEY_SACH_GIA = "GIA";
//    public static final String KEY_SACH_ANH = "ANH";
//    public static final String KEY_SACH_LOAI_MALOAI = "LOAI";
public Mon getMonbyID(int id) {
    this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
    String sql = "SELECT * FROM " + TABLE_NAME_MON
            + " WHERE " + KEY_SACH_MASP + " = " + id;
    Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
    Mon user = new Mon();
    if (cursor.moveToFirst()) {
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setTenSach(cursor.getString(1));
        user.setMoney(Float.parseFloat(cursor.getString(2)));
        user.settPhoto(cursor.getBlob(3));

    }
    cursor.close();
    this.mSQLiteDatabase.close();
    return user;
}

    public double getDoanhThuTheoThang(){
        double doanhThu = 0;
        String sSQL ="SELECT SUM(tongtien) from (SELECT SUM(MON.GIA * HOADONCHITIET.SOLUONG) as 'tongtien' "
                + "FROM HOADON INNER JOIN HOADONCHITIET on HOADON.MAHOADON = HOADONCHITIET.MAHOADON "
                + "INNER JOIN MON on HOADONCHITIET.MASP = MON.MASP where HOADON.THOIGIAN = date('now') GROUP BY HOADONCHITIET.MASP)tmp";
        Cursor c = mSQLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public float tienDoanhthuTheoNgay(String ngayBatDau , String ngayKetThuc){
        float roundOff = 0;
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT SUM(tongtien) FROM ( SELECT HOADONCHITIET.GIADH AS TONGTIEN FROM " +
                TABLE_NAME_MON +" JOIN HOADONCHITIET ON HOADONCHITIET.MASP = MON.MASP "
                +" WHERE HOADON.THOIGIAN BETWEEN  '"+ngayBatDau+" ' AND + '"+ ngayKetThuc +"' )";

        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(cursor.getString(0) !=null) {
                    roundOff = Float.parseFloat(cursor.getString(0));
                }else{
                    roundOff = 0;
                    break;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();

        return roundOff;
    }


}
