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
import com.example.cafemanager.dao.DaoController;
import com.example.cafemanager.model.NhanVien;


import java.util.List;

public class AdapterNV extends RecyclerView.Adapter<AdapterNV.ViewHolder>{
    private DaoController mDaoThuTHu;
    private List<NhanVien> list;
    private Listener listener;
    private Context mContext;
    public AdapterNV(Context context){
        this.mContext = context;
        this.mDaoThuTHu = new DaoController(context);

    }
    public void setData(List<NhanVien> list){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lib , parent , false);
                return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhanVien thuthu = this.list.get(position);
        if(thuthu !=null){
//            byte[] imageuuuu = thuthu.getThuthuPhoto();
//            if(imageuuuu !=null){
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageuuuu, 0, imageuuuu.length);
//                holder.imageLib.setImageBitmap(bitmap);
//            }
            holder.ivDelete.setOnClickListener(view ->{
                listener.onClickDelete(view , position);
            });
            holder.ivEdit.setOnClickListener(view ->{
                listener.onClickEdit(view , position);
            });

            holder.username.setText(thuthu.getSDT());
            holder.nameLib.setText(thuthu.getHoTenThuThu());
//            if(position % 2 ==0){
//                holder.username.setTextColor(Color.WHITE);
//            }else {
//                holder.username.setTextColor(Color.WHITE);
//            }
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView idMember;
        private TextView nameLib;
        private TextView username;
        private ImageView ivEdit;
        private ImageView ivDelete;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            idMember = (CardView) itemView.findViewById(R.id.idMember);
            nameLib = (TextView) itemView.findViewById(R.id.nameLib);
            username = (TextView) itemView.findViewById(R.id.username);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            ivDelete = (ImageView)itemView. findViewById(R.id.ivDeletecong);



        }
    }
}
