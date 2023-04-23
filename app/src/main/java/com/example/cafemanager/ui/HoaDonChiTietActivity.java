package com.example.cafemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanager.MainActivity;
import com.example.cafemanager.R;

import com.example.cafemanager.dao.DAODHchitiet;
import com.example.cafemanager.dao.DAOMon;

import com.example.cafemanager.model.HDCT;

import com.example.cafemanager.model.Mon;
import com.example.cafemanager.ui.Adapter.AdapterCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietActivity extends AppCompatActivity {
    TextView tvThanhTien;
    private DAODHchitiet mDaoBook;
    private AdapterCart mAdapterBook;
    private List<HDCT> mListMon;
    DAOMon sachDAO;
    RecyclerView lvCart;
    double thanhTien = 0;
    public static float thongke;
    Button btnthanhtoan,btnthem;
    int checkmon=0;
    int soluong=0;
    public static int mahoadon;
    float giasp;
    boolean test=true;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        lvCart =  findViewById(R.id.lvCart);
        tvThanhTien =  findViewById(R.id.tvThanhTien);
        btnthanhtoan = findViewById(R.id.btnThanhtoan);
        btnthem =  findViewById(R.id.Themhang);
        if (GiohangActivity.tt==0){
            Intent in = getIntent();
            Bundle b = in.getExtras();
            mahoadon=b.getInt("MAHD");
        }




        Log.e("ma 2",""+mahoadon);
        mDaoBook = new DAODHchitiet(HoaDonChiTietActivity.this);
        mAdapterBook = new AdapterCart(getApplicationContext());
        mListMon = new ArrayList<>();
        mAdapterBook.setListener(this::onClickDelete);


        mListMon =   mDaoBook.getByid(String.valueOf(mahoadon));
        mAdapterBook.setData(this.mListMon);
        lvCart.setAdapter(this.mAdapterBook);
        lvCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent in1 = getIntent();
        Bundle b1 = in1.getExtras();
        if (b1 != null) {
            checkmon = b1.getInt("Key_id", 0);
            soluong=b1.getInt("soluong",0);
            giasp=b1.getFloat("giasp",0);
        }

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test) {

                    GiohangActivity.tt=0;
                    Intent intent = new Intent(HoaDonChiTietActivity.this, GiohangActivity.class);
                    startActivity(intent);

                }

            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListMon.size()<1){
                    Toast.makeText(getApplicationContext(), "Bạn chưa thêm món nào",
                        Toast.LENGTH_SHORT).show();
                }else {
                    thanhToanHoaDon();
                    GiohangActivity.tt=0;
                }


            }
        });


    }


    @Override
    protected void onStart() {

        if(GiohangActivity.tt==1){
            addHoadon();
            GiohangActivity.tt=0;
        }

        super.onStart();
    }



    public void addHoadon() {

        mDaoBook = new DAODHchitiet(HoaDonChiTietActivity.this);
        sachDAO = new DAOMon(HoaDonChiTietActivity.this);

        Mon monnew = sachDAO.getMonbyID(checkmon+1);



        try {


                    HDCT hoaDonChiTiet = new
                            HDCT(0,mahoadon,monnew.getTenSach(),soluong,giasp);
                   mDaoBook.themcong(hoaDonChiTiet);
                   showdata();
        }catch (Exception e){
           Log.e("m", "EROR");
        }
    }
    public void onClickDelete(View view, int position) {
        HDCT kind = mListMon.get(position);
        AlertDialog dialog = new AlertDialog.Builder(HoaDonChiTietActivity.this).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok" , null).setNegativeButton("Cancel" , null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoBook.deletecong(kind.getMaHDCT());
                Toast.makeText(getApplicationContext(), "Delete Successful"  , Toast.LENGTH_SHORT).show();
                showdata();

                dialog.cancel();

            }
        });
    }
   void  showdata(){
       mListMon = (ArrayList<HDCT>)  mDaoBook.getByid(String.valueOf(mahoadon));
       mAdapterBook.setData(mListMon);
       lvCart.setAdapter(mAdapterBook);
    }

    public void thanhToanHoaDon( ) {
        if (test){
            mDaoBook = new DAODHchitiet(HoaDonChiTietActivity.this);
            //tinh tien
            thanhTien = 0;
            try {
                for (HDCT hd: mListMon) {

                    thanhTien = thanhTien + hd.getSoLuongMua() *
                            hd.getGia();
                }
                tvThanhTien.setText("Tổng hóa đơn: "+decimalFormat.format(thanhTien)+" vnđ");
                thongke += thanhTien;
            } catch (Exception ex) {
                Log.e("Error", ex.toString());
            }
        }
        if (test==false){
            HomeActivity.sobanquaMonfragment=0;
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);


        }
        test=false;
    }

}