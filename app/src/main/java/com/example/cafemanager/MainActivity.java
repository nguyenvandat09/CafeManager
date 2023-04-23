package com.example.cafemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.cafemanager.databinding.ActivityMainBinding;
import com.example.cafemanager.model.NhanVien;
import com.example.cafemanager.ui.Fragment.MonFragment;
import com.example.cafemanager.ui.Fragment.KindofbookFragment;
import com.example.cafemanager.ui.HomeActivity;
import com.example.cafemanager.ui.LoanSlipActivity;
import com.example.cafemanager.ui.LoginActivity;
import com.example.cafemanager.ui.MemberActivity;
import com.example.cafemanager.ui.RegisterActivity;
import com.example.cafemanager.ui.RevenueActivity;
import com.google.android.material.appbar.MaterialToolbar;

import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private MaterialToolbar toolBar;
    private LinearLayout idPhieuMuon;
    private LinearLayout idLoaiSach;
    private LinearLayout idSach;
    private LinearLayout idThanhVien;
    private LinearLayout idDoanhThu;
    private LinearLayout idDoiMatKhau;
    private LinearLayout idLogout;
    private LinearLayout idHome;
    private NhanVien thuthu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("");
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, binding.drawer, toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        binding.drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        View contentview = binding.drawer.getContentView();
        View menuView = binding.drawer.getMenuView();
        idPhieuMuon = (LinearLayout) menuView.findViewById(R.id.idPhieuMuon);
        idLoaiSach = (LinearLayout) menuView.findViewById(R.id.idLoaiSach);
        idSach = (LinearLayout) menuView.findViewById(R.id.idSach);
        idThanhVien = (LinearLayout) menuView.findViewById(R.id.idThanhVien);

        idDoanhThu = (LinearLayout) menuView.findViewById(R.id.idDoanhThu);
        idDoiMatKhau = (LinearLayout) menuView.findViewById(R.id.idDoiMatKhau);
        idLogout = (LinearLayout) menuView.findViewById(R.id.idLogout);
        idHome = (LinearLayout) menuView.findViewById(R.id.idHome);
        idPhieuMuon.setOnClickListener(this);
        idLoaiSach.setOnClickListener(this);
        idSach.setOnClickListener(this);
        idThanhVien.setOnClickListener(this);

        idDoanhThu.setOnClickListener(this);
        idDoiMatKhau.setOnClickListener(this);
        idLogout.setOnClickListener(this);
        idHome.setOnClickListener(this);
        MonFragment monFragment = new MonFragment();
        replaceFragment(monFragment);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idHome:
                getSupportActionBar().setTitle("");
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.idPhieuMuon:
                startActivity(new Intent(this, LoanSlipActivity.class));
                break;
            case R.id.idLoaiSach:
                getSupportActionBar().setTitle("Kind of Drinks");
                KindofbookFragment kindofbookFragment = new KindofbookFragment();
                replaceFragment(kindofbookFragment);
                break;

            case R.id.idDoanhThu:
                startActivity(new Intent(this, RevenueActivity.class));
                break;
            case R.id.idDoiMatKhau:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.idThanhVien:

                startActivity(new Intent(this, MemberActivity.class));

                break;
            case R.id.idSach:
                getSupportActionBar().setTitle("Drinks");
                MonFragment monFragment = new MonFragment();
                replaceFragment(monFragment);
                break;


            case R.id.idLogout:
                comFirmExit();
                break;
        }
        binding.drawer.closeDrawer();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public void comFirmExit() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Notification").setMessage("Are you sure you want to escape").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public void showInfo(){
        int id = LoginActivity.id;
        Log.e("Login", String.valueOf(id));
        if (id != 1) {
            idThanhVien.setVisibility(View.GONE);
            idDoanhThu.setVisibility(View.GONE);
            idLoaiSach.setVisibility(View.GONE);
        } else {
            idThanhVien.setVisibility(View.VISIBLE);
            idDoanhThu.setVisibility(View.VISIBLE);
            idLoaiSach.setVisibility(View.VISIBLE);
        }
        thuthu = (NhanVien) getIntent().getSerializableExtra("thuthu");
        if (thuthu!=null) {
            if (thuthu.getTaiKhoan().equals("nguyenvandat")) {
                binding.idUser.setText(thuthu.getHoTenThuThu() + " (Chủ Cửa Hàng)");

            } else {
                binding.idUser.setText(thuthu.getHoTenThuThu());

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }
}