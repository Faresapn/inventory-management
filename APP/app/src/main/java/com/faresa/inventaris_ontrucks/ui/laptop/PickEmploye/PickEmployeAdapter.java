package com.faresa.inventaris_ontrucks.ui.laptop.PickEmploye;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiDataItem;
import com.faresa.inventaris_ontrucks.ui.laptop.CreateLaptopActivity;

import java.util.List;

public class PickEmployeAdapter extends RecyclerView.Adapter<PickEmployeAdapter.ViewHolderPick> {
    private List<PegawaiDataItem> pegawaiGets;
    private Context context;
    private PickEmployeActivity pickEmployeActivity;
    private String imageUrl = "https://pkl-api.adsmall.id/storage/file/pegawai/";


    public PickEmployeAdapter(PickEmployeActivity pickEmployeActivity, Context context) {
        this.pickEmployeActivity = pickEmployeActivity;
        this.context = context;
    }


    public void setPegawaiGets(List<PegawaiDataItem> dataGets) {
        this.pegawaiGets = dataGets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PickEmployeAdapter.ViewHolderPick onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pick, parent, false);
        return new ViewHolderPick(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PickEmployeAdapter.ViewHolderPick holder, int position) {
        holder.nama.setText(pegawaiGets.get(position).getName());
        holder.divisi.setText(holder.itemView.getContext().getString(R.string.divisi) + " " + pegawaiGets.get(position).getDivisiName());
        Glide.with(context).load(imageUrl + pegawaiGets.get(position).getPath()).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return pegawaiGets.size();
    }

    public class ViewHolderPick extends RecyclerView.ViewHolder {
        TextView nama, divisi;
        ImageView photo;

        public ViewHolderPick(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            divisi = itemView.findViewById(R.id.divisi);
            photo = itemView.findViewById(R.id.photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        PegawaiDataItem data = pegawaiGets.get(position);
                        Intent intent = new Intent(view.getContext(), CreateLaptopActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("type_extra", pickEmployeActivity.type);
                        intent.putExtra("os_extra", pickEmployeActivity.os);
                        intent.putExtra("serial_extra", pickEmployeActivity.serial);
                        intent.putExtra("code_extra", pickEmployeActivity.code);
                        intent.putExtra("data", data);
                        intent.putExtra("id", data.getDivisiId());
                        view.getContext().startActivity(intent);

                    }
                }
            });
        }
    }
}
