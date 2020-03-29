package com.faresa.inventaris_ontrucks.ui.laptop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.laptop.delete.LaptopDeleteResponse;
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetData;
import com.faresa.inventaris_ontrucks.ui.laptop.detail.DetailLaptop;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolderDivLaptop> {
    private List<LaptopGetData> dataGets;
    private SharedPrefManager sharedPrefManager;


    void setDataGets(List<LaptopGetData> dataGets) {
        this.dataGets = dataGets;
    }

    private LaptopFragment fragment;


    LaptopAdapter(LaptopFragment laptopFragment, SharedPrefManager sharedPrefManager) {
        this.fragment = laptopFragment;
        this.sharedPrefManager = sharedPrefManager;
    }


    @NonNull
    @Override
    public LaptopAdapter.ViewHolderDivLaptop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlaptop, parent, false);
        return new ViewHolderDivLaptop(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final LaptopAdapter.ViewHolderDivLaptop holder, final int position) {
        holder.nama.setText(dataGets.get(position).getType());
        holder.holder.setText(" " + dataGets.get(position).getPIC());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup;
                popup = new PopupMenu(fragment.getActivity(), holder.more);
                popup.inflate(R.menu.crud_divisi);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Intent intent = new Intent(fragment.getActivity(), UpdateLaptopActivity.class);
                                intent.putExtra("id", String.valueOf(dataGets.get(position).getLaptopId()));
                                intent.putExtra("name", dataGets.get(position).getType());
                                intent.putExtra("data", dataGets.get(position));

                                Objects.requireNonNull(fragment.getActivity()).startActivity(intent);
                                return true;
                            case R.id.delete:
                                sharedPrefManager = new SharedPrefManager(view.getContext());
                                String token = sharedPrefManager.getSpToken();
                                Service service = Client.getClient().create(Service.class);
                                Log.d("asu", String.valueOf(dataGets.get(position).getLaptopId()));
                                Call<LaptopDeleteResponse> delete = service.deleteLaptop("Bearer " + token, dataGets.get(position).getLaptopId(), dataGets.get(position).getFileId());

                                delete.enqueue(new Callback<LaptopDeleteResponse>() {
                                    @Override
                                    public void onResponse(@NotNull Call<LaptopDeleteResponse> call, @NotNull Response<LaptopDeleteResponse> response) {

                                        assert response.body() != null;
                                        String kode = response.body().getStatusCode();
                                        if (kode.equals("0001")) {
                                            Toast.makeText(fragment.getActivity(), view.getContext().getString(R.string.msg_success), Toast.LENGTH_SHORT).show();
                                            dataGets.remove(dataGets.get(position));
                                            notifyDataSetChanged();
                                            if (dataGets.size() == 0) {
                                                fragment.null_layout.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Toast.makeText(fragment.getActivity(), view.getContext().getString(R.string.msg_error), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<LaptopDeleteResponse> call, @NotNull Throwable t) {

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
        return dataGets.size();
    }

    class ViewHolderDivLaptop extends RecyclerView.ViewHolder {
        TextView nama, more, holder;

        ViewHolderDivLaptop(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.laptopType);
            more = itemView.findViewById(R.id.laptop_more);
            holder = itemView.findViewById(R.id.holder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        LaptopGetData data = dataGets.get(position);
                        Intent intent = new Intent(fragment.getActivity(), DetailLaptop.class);
                        intent.putExtra("laptop", data);
                        intent.putExtra("pegawai", data.getPegawaiId());
                        Objects.requireNonNull(fragment.getActivity()).startActivity(intent);
                    }
                }
            });
        }
    }
}
