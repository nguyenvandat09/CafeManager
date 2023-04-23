package com.example.cafemanager.ui.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOCong;
import com.example.cafemanager.dao.DAODHchitiet;
import com.example.cafemanager.model.Cong;
import com.example.cafemanager.model.HDCT;
import com.example.cafemanager.ui.GiohangActivity;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterCart extends  RecyclerView.Adapter<AdapterCart.ViewHolder> {
    private DAODHchitiet mDaoBook;
    private List<HDCT> list;
    private AdapterCart.Listener listener;
    private Context mContext;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public AdapterCart( Context context) {
        this.mContext = context;
        this.mDaoBook = new DAODHchitiet(context);

    }

    public void setData(List<HDCT> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void setListener(AdapterCart.Listener listener) {
        this.listener = listener;
    }



    public interface Listener {

        void onClickDelete(View view, int position);
    }
    @NonNull
    @Override
    public AdapterCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new AdapterCart.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterCart.ViewHolder holder, int position) {
        HDCT member = this.list.get(position);
        if (member != null) {

            holder.soluong.setText("Số lượng: "+member.getSoLuongMua());
            holder.masach.setText("Tên Món: "+ member.getNamesp());
            holder.gia.setText("Giá món: "+decimalFormat.format(member.getGia())+" vnđ");
            float tongtien= member.getGia()* member.getSoLuongMua();
            holder.Thanhtien.setText("Thành tiền: "+decimalFormat.format(tongtien)+" vnđ");
            holder.ivDelete.setOnClickListener(view -> {
                listener.onClickDelete(view, position);
            });


        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView idMember;
        private TextView masach;
        private TextView gia;
        private TextView soluong;
        private TextView Thanhtien;
        private ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMember = (CardView) itemView.findViewById(R.id.idMember);
            masach = (TextView) itemView.findViewById(R.id.tvMaSach);
            gia = (TextView) itemView.findViewById(R.id.tvgia);
            soluong = (TextView) itemView.findViewById(R.id.tvsoluong);
            Thanhtien = (TextView) itemView.findViewById(R.id.tvthanhtien);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDeleteh);

        }
    }

}
