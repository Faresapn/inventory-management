package com.faresa.inventaris_ontrucks.ui.divisi;

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
import com.faresa.inventaris_ontrucks.pojo.divisi.delete.DivisiDeleteResponse;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DataGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisiAdapter extends RecyclerView.Adapter<DivisiAdapter.ViewHolderDivLaptop> {
    private List<DataGet> dataGets;
    private SharedPrefManager sharedPrefManager;


    void setDataGets(List<DataGet> dataGets) {
        this.dataGets = dataGets;
    }

    private DivisiFragment fragment;

    public DivisiAdapter(DivisiFragment divisiFragment, SharedPrefManager sharedPrefManager) {
        this.sharedPrefManager = sharedPrefManager;
        this.fragment = divisiFragment;
    }


    @NonNull
    @Override
    public DivisiAdapter.ViewHolderDivLaptop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddivisi, parent, false);
        return new ViewHolderDivLaptop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DivisiAdapter.ViewHolderDivLaptop holder, final int position) {
        holder.nama.setText(dataGets.get(position).getName());
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
                                Intent intent = new Intent(fragment.getActivity(), DivisiEdit.class);
                                intent.putExtra("id", String.valueOf(dataGets.get(position).getDivisiId()));
                                intent.putExtra("name", dataGets.get(position).getName());
                                fragment.getActivity().startActivity(intent);
                                return true;
                            case R.id.delete:
                                sharedPrefManager = new SharedPrefManager(view.getContext());
                                String token = sharedPrefManager.getSpToken();
                                Service service = Client.getClient().create(Service.class);
                                Log.d("asu", String.valueOf(dataGets.get(position).getDivisiId()));
                                Call<DivisiDeleteResponse> delete = service.deleteDivison("Bearer " + token, dataGets.get(position).getDivisiId());

                                delete.enqueue(new Callback<DivisiDeleteResponse>() {
                                    @Override
                                    public void onResponse(Call<DivisiDeleteResponse> call, Response<DivisiDeleteResponse> response) {

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
                                    public void onFailure(Call<DivisiDeleteResponse> call, Throwable t) {

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

    public class ViewHolderDivLaptop extends RecyclerView.ViewHolder {
        TextView nama, more;

        public ViewHolderDivLaptop(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.divisi);
            more = itemView.findViewById(R.id.laptop_more);
        }
    }
}
