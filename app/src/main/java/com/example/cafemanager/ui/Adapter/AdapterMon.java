package com.example.cafemanager.ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafemanager.R;
import com.example.cafemanager.dao.DAOMon;

import com.example.cafemanager.model.Mon;
import com.example.cafemanager.ui.AddDrinksActivity;
import com.example.cafemanager.ui.LoginActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterMon extends RecyclerView.Adapter<AdapterMon.ViewHolder>{
    private DAOMon mDaoBook;
    private List<Mon> list;
    private Listener listener;
    private Context mContext;
    private TextView txtEdit;
    private TextView txtAddPhieumuon;
    private TextView txtXoa;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public AdapterMon(Context context){
        this.mContext = context;
        this.mDaoBook = new DAOMon(context);

    }
    public void setData(List<Mon> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setListener(AddDrinksActivity addDrinksActivity) {
    }

    public interface Listener {
        void onClickEdit(View view, int position);
        void onClickDelete(View view, int position);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book , parent , false);
                return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        Mon kind = list.get(position);
        if(kind !=null){
            byte[] imageuuuu = kind.gettPhoto();
            if(imageuuuu !=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageuuuu, 0, imageuuuu.length);
                holder.imageLib.setImageBitmap(bitmap);
            }

            holder.nameBook.setText(kind.getTenSach());
            float roundOff = (float) Math.round(kind.getMoney() * 100) / 100;
            holder.idGiaSachGoc.setText(decimalFormat.format(roundOff) +" VNĐ");

            holder.itemView.setOnClickListener(view ->{
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomsheetcontainer));
                txtEdit = (TextView) bottomSheetView.findViewById(R.id.txt_edit);
                txtXoa = (TextView)bottomSheetView. findViewById(R.id.txt_Xoa);
                int id = LoginActivity.id;
                Log.e("Login", String.valueOf(id));
                if (id != 1) {
                    txtEdit.setVisibility(View.GONE);
                    txtXoa.setVisibility(View.GONE);
                } else {
                    txtEdit.setVisibility(View.VISIBLE);
                    txtXoa.setVisibility(View.VISIBLE);
                }
                txtEdit.setOnClickListener(view1 ->{
                    listener.onClickEdit(view , position);
                    bottomSheetDialog.cancel();
                });

                txtXoa.setOnClickListener(view1 ->{
                    listener.onClickDelete(view , position);
                    bottomSheetDialog.cancel();
                });
                bottomSheetView.findViewById(R.id.txt_Huy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView idMember;
        private TextView nameBook;
        private TextView idGiaSachGoc;
        private ImageView imageLib;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            idMember = (CardView)itemView. findViewById(R.id.idMember);
            nameBook = (TextView)itemView. findViewById(R.id.nameBook);
            idGiaSachGoc = (TextView)itemView. findViewById(R.id.nameKindofBookItem);
            imageLib = (ImageView) itemView.findViewById(R.id.IdMon);


        }
    }
}
