package com.example.cafemanager.ui;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOCong;
import com.example.cafemanager.dao.DaoController;
import com.example.cafemanager.model.Cong;
import com.example.cafemanager.model.NhanVien;
import com.example.cafemanager.ui.Adapter.AdapterCong;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CongActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private Button btnAddKindofbook;
    private Button btnThemLT;
    private DAOCong mDaoBook;
    private DaoController mDaoLOAI;
    private AdapterCong mAdapterBook;
    private List<Cong> mListMon;

    TextView tenNV;
    TextView luongnv;
    TextView xoaT;
    EditText etcong;
    NhanVien nhanVien;
    String item;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    float roundOff=0;
    int e=0;

    public static int countLibri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong);
        btnAddKindofbook = findViewById(R.id.btnAddcong);
        recyclerview=findViewById(R.id.recyclerviewcong);
        tenNV =findViewById(R.id.TenNV);
        luongnv = findViewById(R.id.luongnv);
        nhanVien = (NhanVien) getIntent().getSerializableExtra("thongtin");
         item= String.valueOf(nhanVien.getMaThuThu());
        tenNV.setText(nhanVien.getHoTenThuThu());
        luongnv.setText("Lương: "+decimalFormat.format(nhanVien.getLuong())+" VNĐ");
        luongnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(CongActivity.this).setTitle("Trả lương ").setMessage("Are you sure you want ").setPositiveButton("Ok" , null).setNegativeButton("Cancel" , null).show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(e==0){
                            Toast.makeText(getApplicationContext(), " Successful"  , Toast.LENGTH_SHORT).show();
                            luongnv.setText("Lương: "+decimalFormat.format(nhanVien.getLuong())+" VNĐ");
                            e=1;
                        }else{
                            Toast.makeText(getApplicationContext(), "Bạn đã trả lương tháng này cho nhân viên !", Toast.LENGTH_SHORT).show();
                        }



                        dialog.cancel();
                    }
                });
            }
        });
        mDaoBook = new DAOCong(CongActivity.this);
        mAdapterBook = new AdapterCong(getApplicationContext());
        mListMon = new ArrayList<>();
        mAdapterBook.setListener(this::onClickDelete);


        mListMon = (ArrayList<Cong>)  mDaoBook.getByid(item);
        countLibri = mListMon.size();
        mAdapterBook.setData(this.mListMon);
        recyclerview.setAdapter(this.mAdapterBook);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        btnAddKindofbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showdialog();
            }
        });
    }
    void showdialog(){
        final Dialog dialog = new Dialog(CongActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogthemcong);


        etcong = (EditText) dialog.findViewById(R.id.etcong);


        btnThemLT = (Button) dialog.findViewById(R.id.btnThemcong);
        xoaT = (TextView) dialog.findViewById(R.id.xoaTextc);
        btnThemLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String themText = etcong.getText().toString();

                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                int to=today.month+1;
                String timenow1= today.monthDay+"-"+to+"-"+today.year;

                Cong tc = new Cong(0,nhanVien.getMaThuThu() , timenow1,themText );
                if (mDaoBook.themcong(tc) == true) {
                    mListMon = (ArrayList<Cong>)  mDaoBook.getByid(item);
                    mAdapterBook.setData(mListMon);
                    recyclerview.setAdapter(mAdapterBook);
                    Toast.makeText(getApplicationContext(), "Add Successful!", Toast.LENGTH_SHORT).show();
                    tinhluong();
                    dialog.dismiss();

                } else {
                    Toast.makeText(getApplicationContext(), "Add Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        xoaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    void tinhluong(){
        float luong1thang=nhanVien.getLuong();
      float  luong1ngay= luong1thang/30;
      luong1thang=luong1thang-(luong1ngay*mListMon.size());
       roundOff = (float) Math.round(luong1thang * 100) / 100;

      luongnv.setText("Lương: "+decimalFormat.format(roundOff)+" VNĐ");
    }
    void showdata(){
        mListMon = (ArrayList<Cong>)  mDaoBook.getByid(item);
        mAdapterBook.setData(mListMon);
        recyclerview.setAdapter(mAdapterBook);

    }

    @Override
    protected void onStop() {
        if (roundOff>0){
            NhanVien nv=new NhanVien(nhanVien.getTaiKhoan(),nhanVien.getMaThuThu(),nhanVien.getMaKhau(), nhanVien.getHoTenThuThu(),roundOff,nhanVien.getSDT());
            mDaoLOAI=new DaoController(this);
            mDaoLOAI.editKind(nv);
        }


        super.onStop();
    }

    public void onClickDelete(View view, int position) {
        Cong kind = mListMon.get(position);
        AlertDialog dialog = new AlertDialog.Builder(CongActivity.this).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok" , null).setNegativeButton("Cancel" , null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoBook.deletecong(kind.getId());
                Toast.makeText(getApplicationContext(), "Delete Successful"  , Toast.LENGTH_SHORT).show();
                showdata();

                dialog.cancel();
                tinhluong();
            }
        });
    }



}