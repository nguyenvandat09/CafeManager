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

import com.example.cafemanager.model.Cong;
import com.example.cafemanager.ui.CongActivity;

import java.util.List;

public class AdapterCong extends RecyclerView.Adapter<AdapterCong.ViewHolder>{
    private DAOCong mDaoBook;
    private List<Cong> list;
    private Listener listener;
    private Context mContext;

    public AdapterCong( Context context) {
        this.mContext = context;
        this.mDaoBook = new DAOCong(context);

    }

    public void setData(List<Cong> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }



    public interface Listener {

        void onClickDelete(View view, int position);
    }
    @NonNull
    @Override
    public AdapterCong.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cong, parent, false);
        return new AdapterCong.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterCong.ViewHolder holder, int position) {
        Cong member = this.list.get(position);
        if (member != null) {

            holder.nameMember.setText(member.getNgaynghi());
            holder.namsinhMember.setText(member.getLyDo());

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
        private TextView nameMember;
        private TextView namsinhMember;
        private ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMember = (CardView) itemView.findViewById(R.id.idMember);
            nameMember = (TextView) itemView.findViewById(R.id.ngaynghi);
            namsinhMember = (TextView) itemView.findViewById(R.id.lydonghi);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDeletecong1);

        }
    }
}
