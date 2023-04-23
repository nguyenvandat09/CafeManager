package com.example.cafemanager.ui.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DaoController;
import com.example.cafemanager.model.NhanVien;
import com.example.cafemanager.ui.Adapter.AdapterNV;
import com.example.cafemanager.ui.Adapter.RecyclerItemClickListener;
import com.example.cafemanager.ui.CongActivity;
import com.example.cafemanager.ui.NhanVienActivity;
import com.example.cafemanager.ui.UpdateLibActivity;


import java.util.ArrayList;
import java.util.List;


public class NhanVienFragment extends Fragment implements AdapterNV.Listener {
    private EditText etSearch;
    private RecyclerView recyclerview;

    private Button btnAddKindofbook;

    public static int countLibri;
    private List<NhanVien> listNhanVien;
    private DaoController mDaoThuThu;
    private AdapterNV mAdapterNV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_librarian, container, false);


        etSearch = (EditText) view.findViewById(R.id.etSearch);
        recyclerview =  view.findViewById(R.id.recyclerviewnv);
        btnAddKindofbook = (Button) view.findViewById(R.id.btnAddKindofbook);
        this.mDaoThuThu = new DaoController(getActivity());
        this.mAdapterNV = new AdapterNV(getActivity());
        this.listNhanVien = new ArrayList<>();
        this.mAdapterNV.setListener(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        showData();


        btnAddKindofbook.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), NhanVienActivity.class));
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            this.listNhanVien.clear();
            this.listNhanVien = this.mDaoThuThu.getListSearch(etSearch.getText().toString());
            this.mAdapterNV.setData(this.listNhanVien);
            this.recyclerview.setAdapter(this.mAdapterNV);
            return false;
        });

        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {

            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
                Intent intent  = new Intent(getActivity(), CongActivity.class);
                intent.putExtra("thongtin",listNhanVien.get(position));
                startActivity(intent);


            }
        }));
        return view;
    }


    public void showData() {
        this.listNhanVien = this.mDaoThuThu.getListThuTHu();
        countLibri = listNhanVien.size();
        this.mAdapterNV.setData(this.listNhanVien);
        this.recyclerview.setAdapter(this.mAdapterNV);
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    @Override
    public void onClickEdit(View view, int position) {
        NhanVien thu = this.listNhanVien.get(position);
        if (thu.getMaThuThu() == 1) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("Unable to edit this character").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), UpdateLibActivity.class);
            intent.putExtra("thuthu2", thu);
            startActivity(intent);
        }

    }

    @Override
    public void onClickDelete(View view, int position) {
        NhanVien thuthu = this.listNhanVien.get(position);
        if (thuthu.getMaThuThu() == 1) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("Not delete").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        } else {
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("Do you want to delete this character?").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDaoThuThu.deleteTitle(thuthu.getMaThuThu());
                    Toast.makeText(getActivity(), "Delete successful" + thuthu.getHoTenThuThu(), Toast.LENGTH_SHORT).show();
                    showData();
                    dialog.cancel();
                }
            });
        }
    }
}