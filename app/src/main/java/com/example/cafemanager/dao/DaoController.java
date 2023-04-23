package com.example.cafemanager.dao;

import static com.example.cafemanager.database.MySqlHeper.KEY_THUTHU_HOTEN;

import static com.example.cafemanager.database.MySqlHeper.KEY_THUTHU_LUONG;
import static com.example.cafemanager.database.MySqlHeper.KEY_THUTHU_MANV;
import static com.example.cafemanager.database.MySqlHeper.KEY_THUTHU_MATKHAU;

import static com.example.cafemanager.database.MySqlHeper.KEY_THUTHU_SDT;
import static com.example.cafemanager.database.MySqlHeper.KEY_THUTHU_TAIKHOAN;
import static com.example.cafemanager.database.MySqlHeper.TABLE_NAME_NHANVIEN;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cafemanager.database.MySqlHeper;
import com.example.cafemanager.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class DaoController {
    MySqlHeper mMySqlHeper;
    SQLiteDatabase mSQLiteDatabase;

    public DaoController(Context context) {
        mMySqlHeper = new MySqlHeper(context);
    }

    public NhanVien getUserLogin(String username, String password) {
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_NHANVIEN
                + " WHERE " + KEY_THUTHU_TAIKHOAN + " = '" + username + "' and " + KEY_THUTHU_MATKHAU + " = '" + password + "'";
        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        NhanVien user = new NhanVien();
        if (cursor.moveToFirst()) {
            user.setMaThuThu(Integer.parseInt(cursor.getString(0)));
            user.setHoTenThuThu(cursor.getString(1));
            user.setTaiKhoan(cursor.getString(2));
            user.setMaKhau(cursor.getString(3));
            user.setLuong(cursor.getFloat(4));
            user.setSDT(cursor.getString(5));
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return user;
    }

    public NhanVien getUser(String id) {
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_NHANVIEN
                + " WHERE " + KEY_THUTHU_MANV + " = " + id;
        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        NhanVien user = new NhanVien();
        if (cursor.moveToFirst()) {
            user.setMaThuThu(Integer.parseInt(cursor.getString(0)));
            user.setHoTenThuThu(cursor.getString(1));
            user.setTaiKhoan(cursor.getString(2));
            user.setMaKhau(cursor.getString(3));
            user.setLuong(cursor.getFloat(4));
            user.setSDT(cursor.getString(5));
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return user;
    }

    @SuppressLint("Range")
    public List<NhanVien> getListThuTHu() {
        List<NhanVien> list = new ArrayList<NhanVien>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_NHANVIEN;

        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        NhanVien user;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_THUTHU_MANV)));
                String username = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_TAIKHOAN));
                String tenTHuThu = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_HOTEN));
                String password = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_MATKHAU));
                float luong=Float.parseFloat(cursor.getString(cursor.getColumnIndex(KEY_THUTHU_LUONG)));
                String SDT = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_SDT));
                //byte[] imgByte = cursor.getBlob(cursor.getColumnIndex(KEY_THUTHU_IMAGE));
                user = new NhanVien(username ,id , password , tenTHuThu  ,luong,SDT);
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
        return mSQLiteDatabase.delete(TABLE_NAME_NHANVIEN, KEY_THUTHU_MANV + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean themKind(NhanVien thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_THUTHU_HOTEN, thuthu.getHoTenThuThu());
        values.put(KEY_THUTHU_TAIKHOAN, thuthu.getTaiKhoan());
        values.put(KEY_THUTHU_MATKHAU, thuthu.getMaKhau());
        values.put(KEY_THUTHU_LUONG, thuthu.getLuong());
        values.put(KEY_THUTHU_SDT, thuthu.getSDT());
        long result = this.mSQLiteDatabase.insert(TABLE_NAME_NHANVIEN, null, values);
        if (result <= 0) {
            return false;
        }
        return true;
    }

    public boolean editKind(NhanVien thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_THUTHU_HOTEN, thuthu.getHoTenThuThu());
        values.put(KEY_THUTHU_TAIKHOAN, thuthu.getTaiKhoan());
        values.put(KEY_THUTHU_MATKHAU, thuthu.getMaKhau());
        values.put(KEY_THUTHU_LUONG, thuthu.getLuong());
        values.put(KEY_THUTHU_SDT, thuthu.getSDT());
        int r = this.mSQLiteDatabase.update(TABLE_NAME_NHANVIEN, values, KEY_THUTHU_MANV + "=?", new String[]{String.valueOf(thuthu.getMaThuThu())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    @SuppressLint("Range")
    public List<NhanVien> getListSearch(String tenSachSearch) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        this.mSQLiteDatabase = mMySqlHeper.getReadableDatabase();
        String sql = "SELECT " + TABLE_NAME_NHANVIEN + "." + KEY_THUTHU_MANV +
                " , " + KEY_THUTHU_HOTEN +
                " , " + KEY_THUTHU_TAIKHOAN +
                " , " + KEY_THUTHU_MATKHAU +
                " , " + KEY_THUTHU_LUONG +
                " , " + KEY_THUTHU_SDT +
                " FROM " + TABLE_NAME_NHANVIEN +
                " WHERE " + TABLE_NAME_NHANVIEN + "." + KEY_THUTHU_TAIKHOAN + " LIKE '%" + tenSachSearch + "%'";
        Cursor cursor = this.mSQLiteDatabase.rawQuery(sql, null);
        NhanVien user;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_THUTHU_MANV)));
                String tenTHuThu = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_HOTEN));
                String username = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_TAIKHOAN));
                String password = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_MATKHAU));
                float luong=Float.parseFloat(cursor.getString(cursor.getColumnIndex(KEY_THUTHU_LUONG)));
                String SDT = cursor.getString(cursor.getColumnIndex(KEY_THUTHU_SDT));
                user = new NhanVien(username ,id , password , tenTHuThu  ,luong,SDT);
                list.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.mSQLiteDatabase.close();
        return list;
    }


    public boolean changePassword(NhanVien thuthu) {
        this.mSQLiteDatabase = mMySqlHeper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_THUTHU_MATKHAU, thuthu.getMaKhau());
        int r = this.mSQLiteDatabase.update(TABLE_NAME_NHANVIEN, values, KEY_THUTHU_MANV + "=?", new String[]{String.valueOf(thuthu.getMaThuThu())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
}
