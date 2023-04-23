package com.example.cafemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOMon;
import com.example.cafemanager.dao.DaoLOAI;
import com.example.cafemanager.model.Cong;
import com.example.cafemanager.model.Mon;
import com.example.cafemanager.ui.Adapter.AdapterMon;
import com.example.cafemanager.ui.Adapter.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GiohangActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private EditText etSearch;
    private DAOMon mDaoBook;
    private AdapterMon mAdapterBook;
    private List<Mon> mListMon;
    private int idsp;
    public static String tensp="";
    public static int tt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerviewg);
        etSearch = (EditText) findViewById(R.id.etSearchg);
        this.mAdapterBook = new AdapterMon(getApplicationContext());

        this.mDaoBook = new DAOMon(getApplicationContext());
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        this.mListMon = this.mDaoBook.getBookList();
        this.mAdapterBook.setData(this.mListMon);
        recyclerview.setAdapter(mAdapterBook);
        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                Mon mon=new Mon();
                mon=mListMon.get(position);
                tensp=mon.getTenSach();
               idsp= position;
                showdialog();
                tt=1;
            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever

            }
        }));
    }
    void showdialog(){
        final Dialog dialog = new Dialog(GiohangActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogaddkindofbook);
        TextView title= dialog.findViewById(R.id.titleThemLoai);
        title.setText("");
        EditText etsoluong = (EditText) dialog.findViewById(R.id.etSoluong);
        Button btnThemLT = (Button) dialog.findViewById(R.id.btnsoluong);
        TextView xoa= dialog.findViewById(R.id.xoaTextxoluong);
        btnThemLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon mon = mListMon.get(idsp);

                Intent intent = new Intent(getApplicationContext(), HoaDonChiTietActivity.class);
                intent.putExtra("Key_id", idsp);
                intent.putExtra("soluong",Integer.parseInt(etsoluong.getText().toString()));
                intent.putExtra("giasp", mon.getMoney());
                dialog.dismiss();

                startActivity(intent);


            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }
}