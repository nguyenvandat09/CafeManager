package com.example.cafemanager.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafemanager.R;
import com.example.cafemanager.dao.DaoLOAI;
import com.example.cafemanager.model.Kindof;

import java.util.List;

public class AdapterKind extends RecyclerView.Adapter<AdapterKind.ViewHolder> {
    private DaoLOAI mDaoLOAI;
    private List<Kindof> list;
    private Listener listener;
    private Context mContext;
    public AdapterKind(Context context){
        this.mContext = context;
        this.mDaoLOAI = new DaoLOAI(context);

    }
    public void setData(List<Kindof> list){
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

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kindofbook , parent , false);
                return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kindof kind = list.get(position);
        if(kind !=null){
            holder.nameKindofBookItem.setText(kind.getTenloaiSach());
            holder.ivDelete.setOnClickListener(view ->{
                listener.onClickDelete(view , position);
            });
            holder.ivEdit.setOnClickListener(view ->{
                listener.onClickEdit(view , position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameKindofBookItem;
        private ImageView ivEdit;
        private ImageView ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            nameKindofBookItem = (TextView) itemView.findViewById(R.id.nameKindofBookItem);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDeletecong);

        }
    }
}
