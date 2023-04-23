package com.example.cafemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOdonhang1;
import com.example.cafemanager.model.HoaDon;
import com.example.cafemanager.ui.Adapter.AdapterHoaDon;
import com.example.cafemanager.ui.Adapter.RecyclerItemClickListener;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class LoanSlipActivity extends AppCompatActivity {
    private MaterialToolbar toolBar;

    private Button btnAddKindofbook;
    private RecyclerView recyclerview;
    private DAOdonhang1 mDaoBook;

    private AdapterHoaDon mAdapterBook;
    private List<HoaDon> mListMon;
    public static int thanhTien=0;
    public static int taiban=0;
    public static int mahoadon=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_slip);
        toolBar =  findViewById(R.id.tool_bar);
        initToolBar();


        recyclerview = findViewById(R.id.listviewdon);
        btnAddKindofbook = (Button) findViewById(R.id.btnAddKindofbook);
        mDaoBook = new DAOdonhang1(LoanSlipActivity.this);
        mAdapterBook = new AdapterHoaDon(getApplicationContext());
        mListMon = new ArrayList<>();
        mAdapterBook.setListener(this::onClickDelete);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        showData();
        thanhTien=0;
        taiban=0;
        for (HoaDon h: mListMon) {
            if (h.getSoban()==0){
                thanhTien ++;
            }else {
                taiban++;
            }


        }
        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(LoanSlipActivity.this, recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {

            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
                Intent intent  = new Intent(getApplicationContext(), ListHoaDonChiTietByIDActivity.class);
                intent.putExtra("MAHD", mListMon.get(position));
                startActivity(intent);

            }
        }));
        btnAddKindofbook.setOnClickListener(view ->{

            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            int to=today.month+1;
            int b = (int)(Math.random()*(500-10+1)+10);

            String timenow1=today.hour+":"+ today.minute+" "+ today.monthDay+"-"+to+"-"+today.year;
            HoaDon hd=new HoaDon(b,LoginActivity.id,0,timenow1,0);

            if (mDaoBook.themKind(hd) ) {
                mahoadon= hd.getMaHoaDon();
                Log.e("ma 1",""+mahoadon);
                Intent intent  = new Intent(getApplicationContext(), HoaDonChiTietActivity.class);
                intent.putExtra("MAHD",mahoadon);
                startActivity(intent);

            } else {
//                Toast.makeText(getApplicationContext(), "Thêm thất bại",
//                        Toast.LENGTH_SHORT).show();
            }


        });



    }
    public void showData() {
        this.mListMon = this.mDaoBook.getListHoaDon();
        this.mAdapterBook.setData(this.mListMon);
        this.recyclerview.setAdapter(this.mAdapterBook);
    }

    private void initToolBar() {
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setTitle("List Order");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void onClickDelete(View view, int position) {
        HoaDon kind = mListMon.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanSlipActivity.this);
        builder.setMessage("Do you want remove ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            mDaoBook.deleteTitle(kind.getMaHoaDon());
            Toast.makeText(getApplicationContext(), "Delete Successful"  , Toast.LENGTH_SHORT).show();
            showData();
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



}