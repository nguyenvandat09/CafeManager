package com.example.cafemanager.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOMon;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import soup.neumorphism.NeumorphCardView;

public class RevenueActivity extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private DAOMon mDaoBook;
    private NeumorphCardView btnStartDate;
    private TextView dayStart;
    private TextView monthAndYearStart;
    private NeumorphCardView btnEndDate;
    private TextView dayEnd;
    private TextView monthAndYearEnd;
    private NeumorphCardView btnSubmit;
    private TextView tvRevenue;
    private TextView tvDaTra;
    private TextView tvChuaTra;

    private DatePickerDialog mDatePickerDialog;
    private String tvStartDate , tvEndDate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);


        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        btnStartDate = (NeumorphCardView) findViewById(R.id.btnStartDate);
        dayStart = (TextView) findViewById(R.id.dayStart);
        monthAndYearStart = (TextView) findViewById(R.id.monthAndYearStart);
        btnEndDate = (NeumorphCardView) findViewById(R.id.btnEndDate);
        dayEnd = (TextView) findViewById(R.id.dayEnd);
        monthAndYearEnd = (TextView) findViewById(R.id.monthAndYearEnd);
        btnSubmit = (NeumorphCardView) findViewById(R.id.btnSubmit);
        tvRevenue = (TextView) findViewById(R.id.tvRevenue);
        tvDaTra = (TextView) findViewById(R.id.tvDaTra);
        tvChuaTra = (TextView) findViewById(R.id.tvChuaTra);
        tvDaTra.setText("Mang về: "+LoanSlipActivity.thanhTien);
        tvChuaTra.setText("Tại bàn: "+LoanSlipActivity.taiban);
       // tvRevenue.setText("Revenue: 2,400,000");


        this.mDaoBook = new DAOMon(this);
        initToolBar();
        this.mDaoBook = new DAOMon(this);
        datepiker();



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void datepiker(){
        btnEndDate.setOnClickListener(view ->{
            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int yeat = cal.get(Calendar.YEAR);
            mDatePickerDialog = new DatePickerDialog(this , new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    final int date = i2 ;
                    String monthandYear = i + "-" +(i1 +1 )  ;
                    dayEnd.setText(String.valueOf(date));
                    monthAndYearEnd.setText(monthandYear);
                    tvEndDate = i + "-" + i1 + "-" + i2;
                    checkDate(tvStartDate , tvEndDate);
                    //   Toast.makeText(RevenueActivity.this, tvEndDate, Toast.LENGTH_SHORT).show();
                }
            },yeat , month , day);
            mDatePickerDialog.show();


        });
        btnStartDate.setOnClickListener(view ->{
            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int yeat = cal.get(Calendar.YEAR);
            mDatePickerDialog = new DatePickerDialog(this , new DatePickerDialog.OnDateSetListener(){

                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    final int date = i2 ;
                    String monthandYear = i + "-" +(i1 +1 )  ;
                    dayStart.setText(String.valueOf(date));
                    monthAndYearStart.setText(monthandYear);
                    tvStartDate = i + "-" + i1 + "-" + i2;

                    checkDate(tvStartDate , tvEndDate);
                }
            },yeat , month , day);

            mDatePickerDialog.show();

        });
    }
    private void checkDate(String dateStart , String dateEnd){

        SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (dfm.parse(dateStart).before(dfm.parse(dateEnd))){
                String ngay1 =monthAndYearStart.getText().toString()+ "-" +dayStart.getText().toString();
                String ngay2 =  monthAndYearEnd.getText().toString()+ "-" +dayEnd.getText().toString() ;
              //  float t = mDaoBook.tienDoanhthuTheoNgay(ngay1.trim(),ngay2.trim());


                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

                    tvRevenue.setText("Revenue: "+decimalFormat.format(HoaDonChiTietActivity.thongke)+"vnđ");

                dayEnd.setTextColor(Color.rgb(152,4,45));
                monthAndYearEnd.setTextColor(Color.rgb(152,4,45));
                dayStart.setTextColor(Color.rgb(152,4,45));
                monthAndYearStart.setTextColor(Color.rgb(152,4,45));


            }else if( dfm.parse(dateEnd).before(dfm.parse(dateStart))){
                Toast.makeText(this, "End Date Must Not Be Less Than Start Date", Toast.LENGTH_SHORT).show();
                dayEnd.setTextColor(Color.RED);
                monthAndYearEnd.setTextColor(Color.RED);
                return;
            }else if( dfm.parse(dateStart).equals(dfm.parse(dateEnd))){
                Toast.makeText(this, "The End Date Can't Be The Same As The Start Date", Toast.LENGTH_SHORT).show();
                dayStart.setTextColor(Color.RED);
                monthAndYearStart.setTextColor(Color.RED);
                dayEnd.setTextColor(Color.RED);
                monthAndYearEnd.setTextColor(Color.RED);
                return;
            }
        }catch(Exception e) {

        }
    }


    private void initToolBar() {
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Revenue");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }
}