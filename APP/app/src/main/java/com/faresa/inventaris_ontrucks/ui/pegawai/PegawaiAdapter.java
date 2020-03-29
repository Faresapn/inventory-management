package com.faresa.inventaris_ontrucks.ui.pegawai;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;


import com.faresa.inventaris_ontrucks.pojo.pegawai.delete.PegawaiDeleteResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiDataItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.ViewHolderPegawai> {
    private List<PegawaiDataItem> pegawaiGets;
    private SharedPrefManager sharedPrefManager;


    public List<PegawaiDataItem> getPegawaiGets() {
        return pegawaiGets;
    }

    public void setPegawaiGets(List<PegawaiDataItem> dataGets) {
        this.pegawaiGets = dataGets;
    }

    private PegawaiFragment fragment;

    public PegawaiAdapter(PegawaiFragment pegawaiFragment) {

        this.fragment = pegawaiFragment;
    }


    @NonNull
    @Override
    public PegawaiAdapter.ViewHolderPegawai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_employe, parent, false);
        return new ViewHolderPegawai(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PegawaiAdapter.ViewHolderPegawai holder, final int position) {

        holder.nama.setText(pegawaiGets.get(position).getName());
        holder.bind(pegawaiGets.get(position));
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(fragment.getActivity(), holder.more);
                popup.inflate(R.menu.crud_divisi);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Intent intent = new Intent(fragment.getActivity(), EditPegawai.class);
                                intent.putExtra("pegawai_id", String.valueOf(pegawaiGets.get(position).getPegawaiId()));
                                intent.putExtra("divisi_name", pegawaiGets.get(position).getDivisiName());
                                intent.putExtra("divisi_id", pegawaiGets.get(position).getDivisiId());
                                intent.putExtra("file_id", pegawaiGets.get(position).getFileId());
                                intent.putExtra("data", pegawaiGets.get(position));

                                fragment.getActivity().startActivity(intent);
                                return true;
                            case R.id.delete:
                                sharedPrefManager = new SharedPrefManager(view.getContext());
                                String token = sharedPrefManager.getSpToken();
                                Service service = Client.getClient().create(Service.class);
                                Call<PegawaiDeleteResponse> delete = service.deletePegawai("Bearer " + token, pegawaiGets.get(position).getPegawaiId(), pegawaiGets.get(position).getFileId());
                                Log.d("bjir", String.valueOf(pegawaiGets.get(position).getFileId()));

                                delete.enqueue(new Callback<PegawaiDeleteResponse>() {
                                    Response<PegawaiDeleteResponse> response;
                                    @Override
                                    public void onResponse(Call<PegawaiDeleteResponse> call, Response<PegawaiDeleteResponse> response) {
                                        this.response = response;
                                        if (response.isSuccessful()) {
                                            Toast.makeText(fragment.getActivity(), view.getContext().getString(R.string.msg_success_delete), Toast.LENGTH_SHORT).show();
                                            pegawaiGets.remove(pegawaiGets.get(position));
                                            notifyDataSetChanged();
                                            if (pegawaiGets.size() == 0) {
                                                fragment.null_layout.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Toast.makeText(fragment.getActivity(), view.getContext().getString(R.string.msg_error), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PegawaiDeleteResponse> call, Throwable t) {

                                    }
                                });
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pegawaiGets.size();
    }

    public class ViewHolderPegawai extends RecyclerView.ViewHolder {
        TextView nama, more, div;
        ImageView img;

        public ViewHolderPegawai(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.foto_angoota);
            nama = v.findViewById(R.id.nama_anggota);
            more = v.findViewById(R.id.more_anggota);
            div = v.findViewById(R.id.divisii);

        }

        void bind(PegawaiDataItem pegawaiDataItem) {
            div.setText(pegawaiDataItem.getDivisiName());
            Glide.with(itemView.getContext()).load("https://pkl-api.adsmall.id/storage/file/pegawai/" +
                    pegawaiDataItem.getPath()).placeholder(R.drawable.ic_nodata).into(img);

        }
    }
}
