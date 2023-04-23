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
import com.example.cafemanager.dao.DaoMember;
import com.example.cafemanager.model.Member;


import java.text.DecimalFormat;
import java.util.List;

public class AdapterMember extends RecyclerView.Adapter<AdapterMember.ViewHolder> {
    private DaoMember mDaoMember;
    private List<Member> list;
    private Listener listener;
    private Context mContext;
    DecimalFormat decimalFormat = new DecimalFormat("###");
    public AdapterMember(Context context) {
        this.mContext = context;
        this.mDaoMember = new DaoMember(context);

    }

    public void setData(List<Member> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onClickEdit(View view, int position);

        void onClickDelete(View view, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member member = this.list.get(position);
        if (member != null) {

            holder.nameMember.setText(member.getName());
            holder.namsinhMember.setText(member.getSDT());
            float ma = Math.round(member.getSoPhanTramGiam() * 100) / 100;
            holder.sophantram.setText(decimalFormat.format(ma)+" %");

            holder.ivDelete.setOnClickListener(view -> {
                listener.onClickDelete(view, position);
            });
            holder.ivEdit.setOnClickListener(view -> {
                listener.onClickEdit(view, position);
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
        private TextView sophantram;
        private ImageView ivEdit;
        private ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMember = (CardView) itemView.findViewById(R.id.idMember);
            nameMember = (TextView) itemView.findViewById(R.id.nameMem);
            namsinhMember = (TextView) itemView.findViewById(R.id.namsinhMem);
            sophantram= itemView.findViewById(R.id.phantramMem);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDeletecong);

        }
    }
}
