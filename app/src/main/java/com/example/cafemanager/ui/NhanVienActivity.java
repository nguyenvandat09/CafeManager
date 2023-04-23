package com.example.cafemanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cafemanager.R;
import com.example.cafemanager.dao.DaoController;
import com.example.cafemanager.model.NhanVien;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienActivity extends AppCompatActivity {

    private MaterialToolbar toolBar;
    private CircleImageView idImageUser;
    private TextView createTaiKhoan;
    private TextView tvghichu;
    private EditText etHoten;
    private EditText etUsernameRegister;
    private EditText etPasswordRegister;
    private EditText etPassworRegisterdAgin;
    private EditText etLuong;
    private EditText etSDT;
    private Button btDangKi;
    private LinearLayout ll1;
    private TextView textDangNhap;

    private DaoController mDaoTHuThu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        initToolBar();





        idImageUser = (CircleImageView) findViewById(R.id.idImageUser);
        createTaiKhoan = (TextView) findViewById(R.id.createTaiKhoan);
        tvghichu = (TextView) findViewById(R.id.tvghichu);
        etHoten = (EditText) findViewById(R.id.etHoten);
        etUsernameRegister = (EditText) findViewById(R.id.etUsernameRegister);
        etPasswordRegister = (EditText) findViewById(R.id.etmatkhaumoi);
        etPassworRegisterdAgin = (EditText) findViewById(R.id.nhaplaimatkhaumoi);
        etLuong = findViewById(R.id.edtLuong);
        etSDT = findViewById(R.id.edtSDT);
        btDangKi = (Button) findViewById(R.id.btDangKi);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        textDangNhap = (TextView) findViewById(R.id.textDangNhap);
        mDaoTHuThu = new DaoController(this);




        btDangKi.setOnClickListener(view -> {
            String hoten = etHoten.getText().toString();
            String user = etUsernameRegister.getText().toString();
            String password = etPasswordRegister.getText().toString();
            float luong= Float.parseFloat(etLuong.getText().toString());
            String sdt = etSDT.getText().toString();

            String dinhDangSaiVeHo = "((?=.*[a-zA-Z])(?=.*[0-9]).{1,20})";
            String regex = "^[a-zA-Z0-9]*$";
            if (etUsernameRegister.getText().toString().isEmpty() || etUsernameRegister.getText().toString() == null ||  etUsernameRegister.getText().toString().length()<8
                    ||  etUsernameRegister.getText().toString().length() >30 || etUsernameRegister.getText().toString().matches(regex) == false){
                tvghichu.setText("You Have Not Entered Your Username or have special characters(8 - 30 characters)");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (etPasswordRegister.getText().toString().isEmpty() || etPasswordRegister.getText().toString() == null ||  etPasswordRegister.getText().toString().length() <8
                ||  etPasswordRegister.getText().toString().length() >30) {
                tvghichu.setText("You Have Not Entered Password (8 - 30 characters)");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (etPassworRegisterdAgin.getText().toString().isEmpty() || etPassworRegisterdAgin.getText().toString() == null) {
                tvghichu.setText("You Have Not Re-entered Password");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (etHoten.getText().toString().isEmpty() || etHoten.getText().toString() == null) {
                tvghichu.setText("You Have Not Entered  Name");
                tvghichu.setTextColor(Color.RED);
                return;
            }else if(etLuong.getText().toString().isEmpty()){
                tvghichu.setText("You Have Not Entered Salary");
                tvghichu.setTextColor(Color.RED);
                return;
            }
            else if (etLuong.getText().toString().matches("^[a-z].*")){
                tvghichu.setText("Salary  must be number ");
                tvghichu.setTextColor(Color.RED);
                return;
            }
            else if (etPasswordRegister.getText().toString().equals(etPassworRegisterdAgin.getText().toString()) == false) {
                tvghichu.setText("Re-enter Password Do Not Duplicate");
                tvghichu.setTextColor(Color.RED);
                etPasswordRegister.setText("");
                etPassworRegisterdAgin.setText("");
                return;
            }  else if (etHoten.getText().toString().matches("^[A-Z].*") == false) {
                etHoten.setError("First Name Must Capitalize");
                return;
            } else if (etHoten.getText().toString().matches(dinhDangSaiVeHo) == true) {
                etHoten.setError("Name No number");
                return;
            } else if (etHoten.getText().toString().length() < 5) {
                etHoten.setError("Username must be at least 5 . long");
                return;
            } else if (etHoten.getText().toString().length() > 15) {
                etHoten.setError("Username must be up to 15 . in length");
                return;
            } else {
                NhanVien thutthu = new NhanVien(user, 0, password, hoten, luong,sdt);
                if (mDaoTHuThu.themKind(thutthu) == true) {
                    Toast.makeText(this, "More success!", Toast.LENGTH_SHORT).show();
                    this.onBackPressed();

                } else {
                    Toast.makeText(this, "More failure!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void initToolBar() {
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }


}