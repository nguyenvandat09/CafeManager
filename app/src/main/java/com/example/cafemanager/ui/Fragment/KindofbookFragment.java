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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafemanager.R;
import com.example.cafemanager.dao.DaoLOAI;
import com.example.cafemanager.model.Kindof;
import com.example.cafemanager.ui.Adapter.AdapterKind;

import java.util.List;


public class KindofbookFragment extends Fragment implements AdapterKind.Listener {
    private TextView titleThemLoai;
    private EditText etLoaiSach;
    private Button btnThemLT;
    private TextView xoaTextLT;

    private RecyclerView recyclerview;
    private Button btnAddKindofbook;
    private DaoLOAI mDaoLOAI;
    private AdapterKind mAdapterKind;
    private List<Kindof> mKindofBookList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kindofbook, container, false);


        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        btnAddKindofbook = (Button) view.findViewById(R.id.btnAddKindofbook);


        this.mAdapterKind = new AdapterKind(getActivity());
        this.mAdapterKind.setListener(this);
        this.mDaoLOAI = new DaoLOAI(getActivity());
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        showData();
        btnAddKindofbook.setOnClickListener(view1 -> {
            final Dialog dialog = new Dialog(getContext());
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogaddkindofbook);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }


            titleThemLoai = (TextView) dialog.findViewById(R.id.titleThemLoai);
            etLoaiSach = (EditText) dialog.findViewById(R.id.etLoaiSach);
            btnThemLT = (Button) dialog.findViewById(R.id.btnThemLT);
            xoaTextLT = (TextView) dialog.findViewById(R.id.xoaTextLT);

            btnThemLT.setOnClickListener(view2 -> {
                String themText = etLoaiSach.getText().toString();


                if (themText.isEmpty() || themText.toString() == null) {
                    Toast.makeText(getActivity(), "cannot be left blank", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Kindof tc = new Kindof(0, themText);
                    if (mDaoLOAI.themKind(tc) == true) {
                        showData();
                        Toast.makeText(getActivity(), "Add successful!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Add failed!", Toast.LENGTH_SHORT).show();
                    }
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


        return view;
    }

    public void showData() {

        this.mKindofBookList = this.mDaoLOAI.getListOfKindofBooks();
        this.mAdapterKind.setData(mKindofBookList);
        recyclerview.setAdapter(mAdapterKind);
    }

    @Override
    public void onClickEdit(View view, int position) {
        Kindof kind = mKindofBookList.get(position);
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogaddkindofbook);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        titleThemLoai = (TextView) dialog.findViewById(R.id.titleThemLoai);
        etLoaiSach = (EditText) dialog.findViewById(R.id.etLoaiSach);
        btnThemLT = (Button) dialog.findViewById(R.id.btnThemLT);
        xoaTextLT = (TextView) dialog.findViewById(R.id.xoaTextLT);
        titleThemLoai.setText("Edit Kind");
        btnThemLT.setText("Edit");
        etLoaiSach.setText(kind.getTenloaiSach());
        btnThemLT.setOnClickListener(view2 -> {
            String themText = etLoaiSach.getText().toString();
            Kindof tc = new Kindof(kind.getIdLoaiSach(), themText);
            if (mDaoLOAI.editKind(tc) == true) {
                showData();
                Toast.makeText(getActivity(), "Successful fix!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(getActivity(), "Failed fix!", Toast.LENGTH_SHORT).show();
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
        Kindof kind = mKindofBookList.get(position);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("\n" +
                "Are you sure you want to remove").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDaoLOAI.deleteTitle(kind.getIdLoaiSach());
                Toast.makeText(getActivity(), "Delete Thành Công" + kind.getTenloaiSach(), Toast.LENGTH_SHORT).show();
                showData();
                dialog.cancel();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();

    }
}