package com.example.cafemanager.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOdonhang1;
import com.example.cafemanager.model.HoaDon;
import com.example.cafemanager.ui.Adapter.Adapterban;
import com.example.cafemanager.ui.Adapter.RecyclerItemClickListener;
import com.example.cafemanager.ui.Fragment.MonFragment;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private RecyclerView.Adapter adapter;
    private TextView addban;
    private RecyclerView recyclerview;
    private ArrayList<String> planetList=new ArrayList();
    int ban=0;
    int test=0;
    public static int sobanquaMonfragment=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        initToolBar();
        recyclerview = (RecyclerView) findViewById(R.id.recyclerviewban);
        addban= findViewById(R.id.etaddsoban);

        addban.setOnEditorActionListener((v, actionId, event) -> {
            addban();
            Log.e(""," testsetonEdit");
            recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            adapter = new Adapterban(planetList,getApplicationContext());
            recyclerview.setAdapter(adapter);

            return false;
        });
        int id = LoginActivity.id;

        if (id != 1) {
            addban.setVisibility(View.GONE);
        } else {
            addban.setVisibility(View.VISIBLE);
        }

        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new Adapterban(planetList,getApplicationContext());
        recyclerview.setAdapter(adapter);
        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                sobanquaMonfragment= position+1;
                DAOdonhang1 mDaoBook=new DAOdonhang1(HomeActivity.this);
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                int to=today.month+1;
                String timenow1=today.hour+":"+ today.minute+" "+ today.monthDay+"-"+to+"-"+today.year;
                int b = (int)(Math.random()*(1000-500+1)+500);
                test=b;
                Log.e("ma 2",""+test);
                HoaDon hd=new HoaDon(b,LoginActivity.id,0,timenow1,position+1);

                if (mDaoBook.themKind(hd) ) {
                    //mahoadon= hd.getMaHoaDon();
                    Intent intent  = new Intent(getApplicationContext(), HoaDonChiTietActivity.class);
                    intent.putExtra("MAHD",test);
                    startActivity(intent);

                } else {
                    Intent intent  = new Intent(getApplicationContext(), HoaDonChiTietActivity.class);
                    intent.putExtra("MAHD",test);
                    startActivity(intent);
                }
            }
            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));
    }


    void laythongtin(){
        SharedPreferences sharedPreferences = getSharedPreferences("luusoban", MODE_PRIVATE);
       int sobannew=0;
       sobannew=sharedPreferences.getInt("soban",0);
        if(ban>=0){
            planetList.clear();
        }
        for (int i=1;i<=sobannew;i++){
            planetList.add(String.valueOf(i));
        }
        addban.setText(sobannew+"");

        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new Adapterban(planetList,getApplicationContext());
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences("luusoban", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("soban", Integer.parseInt(addban.getText().toString()));
        editor.commit();
        //laythongtin();
        super.onStop();
    }

    @Override
    protected void onResume() {

         laythongtin();

        Log.e(""," testm");

        super.onResume();
    }

    void addban(){
        if(ban>=0){
            planetList.clear();
        }
             ban=Integer.parseInt(addban.getText().toString());

        for (int i=1;i<=ban;i++){
            planetList.add(String.valueOf(i));
        }
    }
    private void initToolBar() {
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Home");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }
}
