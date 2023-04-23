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
import com.example.cafemanager.dao.DAOdonhang1;
import com.example.cafemanager.model.Cong;
import com.example.cafemanager.model.HoaDon;
import com.example.cafemanager.ui.LoginActivity;

import java.util.List;

public class AdapterHoaDon extends  RecyclerView.Adapter<AdapterHoaDon.ViewHolder> {
private DAOdonhang1 mDaoBook;
private List<HoaDon> list;
private Listener listener;
private Context mContext;

    public AdapterHoaDon( Context context) {
        this.mContext = context;
        this.mDaoBook = new DAOdonhang1(context);

    }

    public void setData(List<HoaDon> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void setListener(AdapterHoaDon.Listener listener) {
        this.listener = listener;
    }



    public interface Listener {

        void onClickDelete(View view, int position);
    }
    @NonNull
    @Override
    public AdapterHoaDon.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loanslip, parent, false);
        return new AdapterHoaDon.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterHoaDon.ViewHolder holder, int position) {
        HoaDon member = this.list.get(position);
        if (member != null) {

                holder.tennv.setText(LoginActivity.name);
            holder.thoigian.setText(""+member.getNgayMua());
            if (member.getSoban()==0){
                holder.soban.setText("Mang đi");
            }else {
                holder.soban.setText("Số Bàn: "+member.getSoban());
            }
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
        private TextView tennv;
        private TextView thoigian;
        private TextView soban;
        private ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tennv = (TextView) itemView.findViewById(R.id.teni);
            thoigian = (TextView) itemView.findViewById(R.id.thoigiani);
            soban = itemView.findViewById(R.id.sobani);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDeletei);

        }
    }


}
