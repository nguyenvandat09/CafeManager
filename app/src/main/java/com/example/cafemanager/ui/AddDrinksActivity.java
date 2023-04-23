package com.example.cafemanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOMon;
import com.example.cafemanager.dao.DaoLOAI;
import com.example.cafemanager.model.Mon;
import com.example.cafemanager.ui.Adapter.AdapterMon;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddDrinksActivity extends AppCompatActivity  {
    final int REQUEST_CODE_GALLERY = 999;
    private TextView titleAddBook;
    private EditText tvNameBook;
    private EditText tvGiaGoc;
    ImageView img;
    private Button btnThemAnh;
    private Button xoaTextGD;
    private Button btnThemGD;
    private Spinner spLoaiSach;
    private DAOMon mDaoBook;
    private DaoLOAI mDaoLOAI;
    private AdapterMon mAdapterBook;
    private int idLoaiSach;
    private List<String> nameKindOfBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinks);

        titleAddBook = (TextView) findViewById(R.id.titleAddBook);
        tvNameBook = (EditText) findViewById(R.id.tvNameBook);
        tvGiaGoc = (EditText) findViewById(R.id.tvGiaGoc);

        spLoaiSach = (Spinner) findViewById(R.id.spLoaiSach);
        xoaTextGD = (Button) findViewById(R.id.xoaTextGD);
        btnThemGD = (Button) findViewById(R.id.btnThemGD);
        btnThemAnh= findViewById(R.id.chonanhmon);
        img = findViewById(R.id.ThemimgMon);
        this.mAdapterBook = new AdapterMon(getApplicationContext());
        this.mAdapterBook.setListener( this);
        this.mDaoBook = new DAOMon(getApplicationContext());
        this.mDaoLOAI = new DaoLOAI(getApplicationContext());
        nameKindOfBook = new ArrayList<String>();
        btnThemAnh.setOnClickListener(view1 -> {
            ActivityCompat.requestPermissions(
                    AddDrinksActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
        });
        initSpinner();
        xoaTextGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnThemGD.setOnClickListener(view3 -> {

            if (tvNameBook.getText().toString().isEmpty() || tvNameBook.getText().toString() == null || String.valueOf(tvGiaGoc.getText().toString()).isEmpty() || String.valueOf(tvGiaGoc.getText().toString()) == null  ) {
                Toast.makeText(getApplicationContext(), "Cannot be left blank", Toast.LENGTH_SHORT).show();
                return;
            }else if (spLoaiSach.getCount() == 0){
                Toast.makeText(getApplicationContext(), "No type of drinks", Toast.LENGTH_SHORT).show();
                return;
            }else {
                String nameBook = tvNameBook.getText().toString();
                float giagoc = Float.parseFloat(tvGiaGoc.getText().toString().trim());

                String tenLoai = spLoaiSach.getSelectedItem().toString();

                Mon tc = new Mon(0, nameBook, tenLoai, giagoc,imageViewToByte(img) , idLoaiSach);
                if (mDaoBook.themBook(tc) == true) {

                    Toast.makeText(getApplicationContext(), "Add successful!", Toast.LENGTH_SHORT).show();

                     onBackPressed();

                } else {
                    Toast.makeText(getApplicationContext(), "Add failed!", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initSpinner() {
        this.spLoaiSach.setAdapter(new ArrayAdapter<String>(AddDrinksActivity.this, android.R.layout.simple_spinner_dropdown_item, mDaoLOAI.getListNameKindOfBook()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((CheckedTextView) view).setTextColor(getResources().getColor(R.color.colorXanhSang));
                ((CheckedTextView) view).setTextSize(15);
                return view;
            }
        });
        this.spLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idLoaiSach = mDaoLOAI.getIdLoaiSach(spLoaiSach.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}