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

public class UpdateLibActivity extends AppCompatActivity {

    private MaterialToolbar toolBar;
    private LinearLayout contenImage;
    private Button chonanh;
    private TextView createTaiKhoan;
    private TextView tvghichu;
    private EditText etHoten;
    private EditText etUsernameRegister;
    private EditText etPasswordRegister;
    private EditText etPassworRegisterdAgin;
    private EditText etluong,etSDT;
    private Button btDangKi;
    private LinearLayout ll1;
    private TextView textDangNhap;
    private DaoController mDaoTHuThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lib);

        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        initToolBar();
        contenImage = (LinearLayout) findViewById(R.id.contenImage);


        createTaiKhoan = (TextView) findViewById(R.id.createTaiKhoan);
        tvghichu = (TextView) findViewById(R.id.tvghichu);
        etHoten = (EditText) findViewById(R.id.etHoten);
        etUsernameRegister = (EditText) findViewById(R.id.etUsernameRegister);
        etPasswordRegister = (EditText) findViewById(R.id.etmatkhaumoi);
        etPassworRegisterdAgin = (EditText) findViewById(R.id.nhaplaimatkhaumoi);
        etluong= findViewById(R.id.edtLuong);
        etSDT=findViewById(R.id.edtSDT);
        btDangKi = (Button) findViewById(R.id.btDangKi);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        textDangNhap = (TextView) findViewById(R.id.textDangNhap);
        NhanVien thuthu1 = (NhanVien) getIntent().getSerializableExtra("thuthu2");
        etHoten.setText(thuthu1.getHoTenThuThu());
        etUsernameRegister.setText(thuthu1.getTaiKhoan());
        etPasswordRegister.setText(thuthu1.getMaKhau());
        etPassworRegisterdAgin.setText(thuthu1.getMaKhau());
        etluong.setText(String.valueOf(thuthu1.getLuong()));
        etSDT.setText(thuthu1.getSDT());
        mDaoTHuThu = new DaoController(this);

        btDangKi.setOnClickListener(view -> {
            String hoten = etHoten.getText().toString();
            String user = etUsernameRegister.getText().toString();
            String password = etPasswordRegister.getText().toString();
            float luong= Float.parseFloat(etluong.getText().toString());
            String sdt= etSDT.getText().toString();

            String regex =  " /[ `!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~]/";

            if (etUsernameRegister.getText().toString().isEmpty() || etUsernameRegister.getText().toString() == null || etUsernameRegister.length() <=8 || etUsernameRegister.length() >30
            || etUsernameRegister.getText().toString().matches(regex)) {
                tvghichu.setText("You Have Not Entered Your Username (8-30 characters)");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (etPasswordRegister.getText().toString().isEmpty() || etPasswordRegister.getText().toString() == null || etPasswordRegister.toString().length() <=8 || etPasswordRegister.toString().length()>30) {
                tvghichu.setText("You Have Not Entered Password (8-30 characters)");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (etPassworRegisterdAgin.getText().toString().isEmpty() || etPassworRegisterdAgin.getText().toString() == null) {
                tvghichu.setText("You Have Not Re-entered Password");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (etPasswordRegister.getText().toString().equals(etPassworRegisterdAgin.getText().toString()) == false) {
                tvghichu.setText("Re-enter Password Do Not Duplicate");
                tvghichu.setTextColor(Color.RED);
                etPasswordRegister.setText("");
                etPassworRegisterdAgin.setText("");
                return;
            }  else {
                NhanVien thutthu = new NhanVien(user, thuthu1.getMaThuThu(), password, hoten, luong,sdt);
                if (mDaoTHuThu.editKind(thutthu) == true) {
                    Toast.makeText(this, "Successful fix!", Toast.LENGTH_SHORT).show();
                    this.onBackPressed();

                } else {
                    Toast.makeText(this, "Fix failure!", Toast.LENGTH_SHORT).show();
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