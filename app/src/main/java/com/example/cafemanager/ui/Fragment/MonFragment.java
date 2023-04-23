package com.example.cafemanager.ui.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOMon;
import com.example.cafemanager.dao.DaoLOAI;
import com.example.cafemanager.model.Mon;
import com.example.cafemanager.ui.Adapter.AdapterMon;
import com.example.cafemanager.ui.AddDrinksActivity;
import com.example.cafemanager.ui.HomeActivity;
import com.example.cafemanager.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;


public class MonFragment extends Fragment implements AdapterMon.Listener {


    private RecyclerView recyclerview;
    private Button btnAddKindofbook;

    private EditText etSearch;
    private DAOMon mDaoBook;
    private DaoLOAI mDaoLOAI;
    private AdapterMon mAdapterBook;
    private List<Mon> mListMon;
    private TextView titleAddBook;
    private EditText tvNameBook;
    private EditText tvGiaGoc;
    private Spinner spLoaiSach;
    private Button xoaTextGD;
    private Button btnThemGD;
    private List<String> nameKindOfBook;
    private int idLoaiSach;
    private int soban=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);



        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        btnAddKindofbook = (Button) view.findViewById(R.id.btnAddKindofbook);
        etSearch = (EditText) view.findViewById(R.id.etSearch);
        soban=HomeActivity.sobanquaMonfragment;


        this.mAdapterBook = new AdapterMon(getActivity());
        this.mAdapterBook.setListener(this);
        this.mDaoBook = new DAOMon(getActivity());
        this.mDaoLOAI = new DaoLOAI(getActivity());
        nameKindOfBook = new ArrayList<String>();
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL , false));
        showData();

        btnAddKindofbook.setOnClickListener(view2 -> {
            startActivity(new Intent(getActivity(), AddDrinksActivity.class));
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            this.mListMon.clear();
            this.mListMon = this.mDaoBook.getListSearch(etSearch.getText().toString());
            this.mAdapterBook.setData(this.mListMon);
            this.recyclerview.setAdapter(this.mAdapterBook);
            return false;
        });

        return view;
    }



    public void showData() {

        this.mListMon = this.mDaoBook.getBookList();
        this.mAdapterBook.setData(this.mListMon);
        recyclerview.setAdapter(mAdapterBook);
    }


    @Override
    public void onResume() {
        super.onResume();
        showData();
        showInfo();

    }
    public void showInfo(){
        int id = LoginActivity.id;
        Log.e("Login", String.valueOf(id));
        if (id != 1) {
            btnAddKindofbook.setVisibility(View.GONE);
        } else {
            btnAddKindofbook.setVisibility(View.VISIBLE);
        }

    }

    public void initSpinner() {
        this.spLoaiSach.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mDaoLOAI.getListNameKindOfBook()) {
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

    @Override
    public void onClickEdit(View view, int position) {
        Mon mon = mListMon.get(position);
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dailogthemmon);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        titleAddBook = (TextView) dialog.findViewById(R.id.titleAddBook);
        tvNameBook = (EditText) dialog.findViewById(R.id.tvNameBook);
        tvGiaGoc = (EditText) dialog.findViewById(R.id.tvGiaGoc);

        spLoaiSach = (Spinner) dialog.findViewById(R.id.spLoaiSach);
        xoaTextGD = (Button) dialog.findViewById(R.id.xoaTextGD);
        btnThemGD = (Button) dialog.findViewById(R.id.btnThemGD);
        titleAddBook.setText("Edit ");
        btnThemGD.setText("Submit");
        tvNameBook.setText(mon.getTenSach());
        tvGiaGoc.setText(String.valueOf(mon.getMoney()));



        initSpinner();
        spLoaiSach.setSelection(mDaoLOAI.getListNameKindOfBook().indexOf(mon.getTenLoaiSach()));

        btnThemGD.setOnClickListener(view2 -> {
            String nameBook = tvNameBook.getText().toString();
            float giagoc = Float.parseFloat(tvGiaGoc.getText().toString());

            String tenLoai = spLoaiSach.getSelectedItem().toString();

            Mon tc = new Mon(mon.getId(), nameBook, tenLoai, giagoc, mon.gettPhoto(), idLoaiSach);
            if (mDaoBook.editBoock(tc) == true) {
                showData();
                Toast.makeText(getActivity(), "Successful fix!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(getActivity(), "Failed fix!", Toast.LENGTH_SHORT).show();
            }
        });

        xoaTextGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onClickDelete(View view, int position) {
        Mon kind = mListMon.get(position);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoBook.deleteTitle(kind.getId());
                mDaoBook.deleteTitle(kind.getId());
                Toast.makeText(getActivity(), "Delete Thành Công" + kind.getTenSach(), Toast.LENGTH_SHORT).show();
                showData();
                dialog.cancel();
            }
        });
    }



}