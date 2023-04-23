package com.example.cafemanager.ui;

import static android.text.TextUtils.isEmpty;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DaoController;
import com.example.cafemanager.model.NhanVien;
import com.google.android.material.appbar.MaterialToolbar;

public class RegisterActivity extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private TextView createTaiKhoan;
    private TextView tvghichu;
    private EditText etmatkhaucu;
    private EditText etmatkhaumoi;
    private EditText nhaplaimatkhaumoi;
    private Button btDangKi;
    private LinearLayout ll1;
    private TextView textDangNhap;
    private DaoController mDaoTHuThu;
    private NhanVien nhanVien2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        initToolBar();
        createTaiKhoan = (TextView) findViewById(R.id.createTaiKhoan);
        tvghichu = (TextView) findViewById(R.id.tvghichu);
        etmatkhaucu = (EditText) findViewById(R.id.etmatkhaucu);
        etmatkhaumoi = (EditText) findViewById(R.id.etmatkhaumoi);
        nhaplaimatkhaumoi = (EditText) findViewById(R.id.nhaplaimatkhaumoi);
        btDangKi = (Button) findViewById(R.id.btDangKi);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        textDangNhap = (TextView) findViewById(R.id.textDangNhap);
        this.mDaoTHuThu = new DaoController(this);
        nhanVien2 = mDaoTHuThu.getUser(String.valueOf(LoginActivity.id));
        btDangKi.setOnClickListener(view -> {

            Dialog dialog = new Dialog(RegisterActivity.this);
            dialog.setContentView(R.layout.showloading);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timerDelayRemoveDialog2(1000, dialog);
            dialog.show();
            if (!etmatkhaucu.getText().toString().equals(nhanVien2.getMaKhau())) {
                tvghichu.setText("Old Password Incorrect");
                tvghichu.setTextColor(Color.RED);
                return;

            } else if (isEmpty(etmatkhaucu.getText().toString()) || isEmpty(etmatkhaumoi.getText().toString()) || isEmpty(nhaplaimatkhaumoi.getText().toString())) {
                tvghichu.setText("Fields cannot be left blank");
                tvghichu.setTextColor(Color.RED);
                return;
            } else if (!etmatkhaumoi.getText().toString().equals(nhaplaimatkhaumoi.getText().toString())) {
                tvghichu.setText("Re-enter Unmatched Password");
                tvghichu.setTextColor(Color.RED);
                return;
            } else {
                NhanVien thutthu1 = new NhanVien(LoginActivity.id, nhaplaimatkhaumoi.getText().toString());
                if (mDaoTHuThu.changePassword(thutthu1) == true) {
                    timerDelayRemoveDialog(1000, dialog, thutthu1);
                    dialog.show();
                    Toast.makeText(this, "Edit successful", Toast.LENGTH_SHORT).show();

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

    private void timerDelayRemoveDialog2(int i, Dialog dialog) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, i);
    }

    private void timerDelayRemoveDialog(int i, Dialog dialog, NhanVien thu) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                onBackPressed();
            }
        }, i);

    }
}