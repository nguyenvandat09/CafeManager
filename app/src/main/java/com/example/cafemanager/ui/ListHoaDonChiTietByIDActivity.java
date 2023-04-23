package com.example.cafemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAODHchitiet;
import com.example.cafemanager.model.HDCT;
import com.example.cafemanager.model.HoaDon;
import com.example.cafemanager.model.NhanVien;
import com.example.cafemanager.ui.Adapter.AdapterCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListHoaDonChiTietByIDActivity extends AppCompatActivity {

    RecyclerView lvCart;
    private DAODHchitiet mDaoBook;
    private AdapterCart mAdapterBook;
    private List<HDCT> mListMon;
    private TextView tongtien;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don_chi_tiet_by_idactivity);
        lvCart = findViewById(R.id.lvHoaDonChiTiet);
        tongtien = findViewById(R.id.tongtien);
        HoaDon ct;
        ct= (HoaDon) getIntent().getSerializableExtra("MAHD");


        mDaoBook = new DAODHchitiet(this);
        mAdapterBook = new AdapterCart(getApplicationContext());
        mListMon = new ArrayList<>();
        mListMon =   mDaoBook.getByid(String.valueOf(ct.getMaHoaDon()));
        mAdapterBook.setData(this.mListMon);


        lvCart.setAdapter(this.mAdapterBook);
        lvCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        float thanhTien=0;
        try {
            for (HDCT hd: mListMon) {

                thanhTien = thanhTien + hd.getSoLuongMua() *
                        hd.getGia();
            }
            tongtien.setText("Tổng hóa đơn: "+decimalFormat.format(thanhTien)+" vnđ");
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }
}