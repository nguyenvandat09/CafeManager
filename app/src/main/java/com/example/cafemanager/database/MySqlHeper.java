package com.example.cafemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanager.dao.DAODHchitiet;
import com.example.cafemanager.dao.DAOdonhang1;

public class MySqlHeper extends SQLiteOpenHelper {

    public static final String TABLE_NAME_KVIP = "KVIP";
    public static final String KEY_THANHVIEN_MAKVIP = "MAKVIP";
    public static final String KEY_THANHVIEN_HOTEN = "HOTEN";
    public static final String KEY_THANHVIEN_SDT = "SDT";
    public static final String KEY_THANHVIEN_SoPhanTramGiam = "SoPhanTramGiam";


    public static final String TABLE_NAME_NHANVIEN = "NHANVIEN";
    public static final String KEY_THUTHU_MANV = "MANV";
    public static final String KEY_THUTHU_TAIKHOAN = "TAIKHOAN";
    public static final String KEY_THUTHU_HOTEN = "HOTEN";
    public static final String KEY_THUTHU_MATKHAU = "MATKHAU";
    public static final String KEY_THUTHU_LUONG = "LUONG";
    public static final String KEY_THUTHU_SDT = "SDT";


    public static final String TABLE_NAME_LOAI = "LOAI";
    public static final String KEY_LOAI_MALOAI = "MALOAI";
    public static final String KEY_LOAI_TENLOAI = "TENLOAI";

    public static final String TABLE_NAME_MON = "MON";
    public static final String KEY_SACH_MASP = "MASP";
    public static final String KEY_SACH_TENSP = "TENSP";
    public static final String KEY_SACH_GIA = "GIA";
    public static final String KEY_SACH_ANH = "ANH";
    public static final String KEY_SACH_LOAI_MALOAI = "LOAI";


    public static final String TABLE_NAME_CONG = "CONG";
    public static final String KEY_CONG_MACONG = "MACONG";
    public static final String KEY_CONG_MANV = "MANV";
    public static final String KEY_CONG_NGAYNGHI = "NGAYNGHI";
    public static final String KEY_CONG_LYDONGHI = "LYDONGHI";

    public static final String TABLE_NAME_HOADON = "HOADON";
    public static final String KEY_HOADON_MA = "MAHOADON";
    public static final String KEY_HOADON_MANV = "MANV";
    public static final String KEY_HOADON_KVIP = "MAKVIP";
    public static final String KEY_HOADON_THOIGIAN = "THOIGIAN";
    public static final String KEY_HOADON_SOBAN = "SOBAN";

    public static final String TABLE_NAME_HOADONCHITIET = "HOADONCHITIET";
    public static final String KEY_CHITIET_MA = "MAHOADONCT";
    public static final String KEY_CHITIET_MAHOADON = "MAHOADON";
    public static final String KEY_CHITIET_MASP= "MASP";
    public static final String KEY_CHITIET_SOLUONG = "SOLUONG";
    public static final String KEY_CHITIET_GIA = "GIAHD";

    public MySqlHeper(Context context) {
        super(context, "quanli.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "CREATE TABLE " + TABLE_NAME_NHANVIEN
                + "("
                + KEY_THUTHU_MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_THUTHU_HOTEN + " TEXT NOT NULL ,"
                + KEY_THUTHU_TAIKHOAN + " TEXT NOT NULL ,"
                + KEY_THUTHU_MATKHAU + " TEXT NOT NULL, "
                + KEY_THUTHU_LUONG + " MONEY, "
                + KEY_THUTHU_SDT + " TEXT NOT NULL "
                + ")";
        sqLiteDatabase.execSQL(sql1);
        sql1 = " CREATE TABLE " + TABLE_NAME_KVIP
                + "("
                + KEY_THANHVIEN_MAKVIP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_THANHVIEN_HOTEN + " TEXT NOT NULL, "
                + KEY_THANHVIEN_SDT + " TEXT NOT NULL, "
                + KEY_THANHVIEN_SoPhanTramGiam+" INTEGER NOT NULL "
                + ")";
        sqLiteDatabase.execSQL(sql1);
        sql1 = "CREATE TABLE " + TABLE_NAME_LOAI
                + "("
                + KEY_LOAI_MALOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LOAI_TENLOAI + " TEXT NOT NULL "
                + ")";
        sqLiteDatabase.execSQL(sql1);
        sql1 = "CREATE TABLE " + TABLE_NAME_MON
                + "("
                + KEY_SACH_MASP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_SACH_TENSP + " TEXT NOT NULL ,"
                + KEY_SACH_GIA + " MONEY NOT NULL ,"
                + KEY_SACH_ANH + " BLOB  ,"
                + KEY_SACH_LOAI_MALOAI + " INTEGER REFERENCES " + TABLE_NAME_LOAI + "( " + KEY_SACH_LOAI_MALOAI + ")"
                + ")";
        sqLiteDatabase.execSQL(sql1);

        sql1 = "CREATE TABLE " + TABLE_NAME_CONG
                + "("
                + KEY_CONG_MACONG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_CONG_MANV + " INTEGER REFERENCES  "+ TABLE_NAME_NHANVIEN + "( " + KEY_CONG_MANV + "),"
                + KEY_CONG_NGAYNGHI + " DATE NOT NULL ,"
                + KEY_CONG_LYDONGHI+ " TEXT NOT NULL "
                + ")";
        sqLiteDatabase.execSQL(sql1);

        sql1 = "CREATE TABLE " + TABLE_NAME_HOADON
                + "("
                + KEY_HOADON_MA + " INTEGER PRIMARY KEY , "
                + KEY_HOADON_MANV + " INTEGER NOT NULL ,"
                + KEY_HOADON_KVIP + " INTEGER ,"
                + KEY_HOADON_THOIGIAN + " TEXT NOT NULL ,"
                + KEY_HOADON_SOBAN + " INTEGER  "
                + ")";
        sqLiteDatabase.execSQL(sql1);

        sql1 = "CREATE TABLE " + TABLE_NAME_HOADONCHITIET
                + "("
                + KEY_CHITIET_MA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_CHITIET_MAHOADON + " INTEGER REFERENCES  "+ TABLE_NAME_HOADON + "( " + KEY_CHITIET_MAHOADON + " ),"
                + KEY_CHITIET_MASP + " TEXT NOT NULL , "
                + KEY_CHITIET_SOLUONG+ " INTEGER NOT NULL ,"
                + KEY_CHITIET_GIA+ " MONEY NOT NULL "
                + ")";
        sqLiteDatabase.execSQL(sql1);


        sql1 = "INSERT INTO " + TABLE_NAME_NHANVIEN + " VALUES ( null ,'Nguyễn Văn Đạt','chucuahang' ,'12345678' , 2000000 ,'012345678' )";
        sqLiteDatabase.execSQL(sql1);
        sql1 = "INSERT INTO " + TABLE_NAME_NHANVIEN + " VALUES ( null ,'Nguyễn Văn Đạt2','nhanvien' ,'12345678' , 2000000 ,'012345678' )";
        sqLiteDatabase.execSQL(sql1);
        sql1 = "INSERT INTO " + TABLE_NAME_LOAI + " VALUES ( null ,'Cafe'  )";
        sqLiteDatabase.execSQL(sql1);
        sql1 = "INSERT INTO " + TABLE_NAME_MON + " VALUES ( null ,'Cafe sữa',12000 ,null , 1  )";
        sqLiteDatabase.execSQL(sql1);
        sql1 = "INSERT INTO " + TABLE_NAME_KVIP + " VALUES ( null,'dat','012312433' ,12   )";
        sqLiteDatabase.execSQL(sql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NHANVIEN);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KVIP);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LOAI);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MON);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONG);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOADON);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOADONCHITIET);
        onCreate(sqLiteDatabase);

    }
}
