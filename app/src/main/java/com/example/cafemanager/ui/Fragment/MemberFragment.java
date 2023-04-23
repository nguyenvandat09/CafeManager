package com.example.cafemanager.ui.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafemanager.R;
import com.example.cafemanager.dao.DaoMember;
import com.example.cafemanager.model.Member;
import com.example.cafemanager.ui.Adapter.AdapterMember;

import java.util.ArrayList;
import java.util.List;


public class MemberFragment extends Fragment implements AdapterMember.Listener{
    private EditText etSearch;
    private RecyclerView recyclerview;
    private Button btnAddKindofbook;
    private TextView titleThemLoai;
    private EditText etLoaiSach;
    private EditText etSDT;
    private Spinner spName;
    private Button btnThemLT;
    private TextView xoaTextLT;
    private List<Integer> listNameSinh;
    private DaoMember mDaoMember;
    private AdapterMember mAdapterMember;
    private List<Member> mmemberList;
    private int namSinh=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member, container, false);


        etSearch = (EditText) view.findViewById(R.id.etSearch);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        btnAddKindofbook = (Button) view.findViewById(R.id.btnAddKindofbook);
        listNameSinh = new ArrayList<>();
        this. mDaoMember = new DaoMember(getActivity());
        this.mAdapterMember = new AdapterMember(getActivity());
        this.mmemberList = new ArrayList<>();
        this.mAdapterMember.setListener(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        showData();
        iniSpiner();
        btnAddKindofbook.setOnClickListener(view1 ->{
            final Dialog dialog = new Dialog(getContext());
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogthemthanhvien);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }


            titleThemLoai = (TextView)dialog. findViewById(R.id.titleThemLoai);
            etLoaiSach = (EditText) dialog.findViewById(R.id.etLoaiSach);
            etSDT = dialog.findViewById(R.id.etSDT);
            spName = (Spinner) dialog.findViewById(R.id.spName);
            btnThemLT = (Button) dialog.findViewById(R.id.btnThemLT);
            xoaTextLT = (TextView) dialog.findViewById(R.id.xoaTextLT);
            spName.setAdapter(new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listNameSinh){
                @Override
                public View getView(int position, View convertView,  ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    ((CheckedTextView) view).setTextColor(getResources().getColor(R.color.colorXanhSang));
                    ((CheckedTextView) view).setTextSize(15);
                    return view;
                }
            });
            spName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    namSinh = Integer.parseInt(spName.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spName.setSelection(1);
            btnThemLT.setOnClickListener(view2 ->{
                String themText = etLoaiSach.getText().toString();
                String sdt = etSDT.getText().toString();
                Member tc = new Member(0, themText ,sdt,namSinh );
                if (mDaoMember.themKind(tc) == true) {
                    showData();
                    Toast.makeText(getActivity(), "Add Successful!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {

                    Toast.makeText(getActivity(), "Add Failed!", Toast.LENGTH_SHORT).show();
                }
            });



            xoaTextLT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            this.mmemberList.clear();
            this.mmemberList = this.mDaoMember.getListSearch(etSearch.getText().toString());
            this.mAdapterMember.setData(this.mmemberList);
            this.recyclerview.setAdapter(this.mAdapterMember);
            return false;
        });
        return view ;
    }
    public void iniSpiner() {
        for(int i = 0 ; i < 100 ; i++){
            listNameSinh.add(i);
        }
    }
    public void showData(){
        this.mmemberList =this.mDaoMember.getListMember();
        this.mAdapterMember.setData(this.mmemberList );
        this.recyclerview.setAdapter(this.mAdapterMember);
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    @Override
    public void onClickEdit(View view, int position) {
        Member kind = mmemberList.get(position);
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogthemthanhvien);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        titleThemLoai = (TextView)dialog. findViewById(R.id.titleThemLoai);
        etLoaiSach = (EditText) dialog.findViewById(R.id.etLoaiSach);
        spName = (Spinner) dialog.findViewById(R.id.spName);
        etSDT = dialog.findViewById(R.id.etSDT);
        btnThemLT = (Button) dialog.findViewById(R.id.btnThemLT);
        xoaTextLT = (TextView) dialog.findViewById(R.id.xoaTextLT);
        titleThemLoai.setText("Edit ");
        btnThemLT.setText("Submit");
        etLoaiSach.setText(kind.getName());
        etSDT.setText(kind.getSDT());
        spName.setAdapter(new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listNameSinh){
            @Override
            public View getView(int position, View convertView,  ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((CheckedTextView) view).setTextColor(getResources().getColor(R.color.colorXanhSang));
                ((CheckedTextView) view).setTextSize(15);
                return view;
            }
        });
        spName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namSinh = Integer.parseInt(spName.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spName.setSelection(listNameSinh.indexOf(kind.getSoPhanTramGiam()));
        btnThemLT.setOnClickListener(view2 ->{
            String themText = etLoaiSach.getText().toString();
            String sdtt= etSDT.getText().toString();
            Member tc = new Member(kind.getId(), themText ,sdtt, namSinh);
            if (mDaoMember.editKind(tc) == true) {
                showData();
                Toast.makeText(getActivity(), "Fix Successful!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(getActivity(), "Fix failed!", Toast.LENGTH_SHORT).show();
            }
        });



        xoaTextLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onClickDelete(View view, int position) {
        Member kind = mmemberList.get(position);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok" , null).setNegativeButton("Cancel" , null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoMember.deleteTitle(kind.getId());
                Toast.makeText(getActivity(), "Delete Successful"  + kind.getName(), Toast.LENGTH_SHORT).show();
                showData();
                dialog.cancel();
            }
        });
    }



}