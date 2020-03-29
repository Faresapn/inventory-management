package com.faresa.inventaris_ontrucks.ui.Printer;

import android.content.Intent;
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
import com.faresa.inventaris_ontrucks.pojo.printer.delete.DeletePrinterResponse;
import com.faresa.inventaris_ontrucks.pojo.printer.get.DataPrinterItem;
import com.faresa.inventaris_ontrucks.ui.Printer.detail.DetailPrinter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrinterAdapter extends RecyclerView.Adapter<PrinterAdapter.ViewHolderPrinter> {
    private List<DataPrinterItem> printerGets;
    private SharedPrefManager sharedPrefManager;

    public List<DataPrinterItem> getPrinterGets() {
        return printerGets;
    }

    public void setPrinterGets(List<DataPrinterItem> dataGets) {
        this.printerGets = dataGets;
    }

    private PrinterFragment fragment;

    public PrinterAdapter(PrinterFragment printerFragment) {

        this.fragment = printerFragment;
        this.sharedPrefManager = sharedPrefManager;
    }

    @NonNull
    @Override
    public PrinterAdapter.ViewHolderPrinter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_printer, parent, false);
        return new PrinterAdapter.ViewHolderPrinter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PrinterAdapter.ViewHolderPrinter holder, final int position) {
        holder.bind(printerGets.get(position));
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
                                Intent intent = new Intent(fragment.getActivity(), EditPrinter.class);
                                intent.putExtra("id", String.valueOf(printerGets.get(position).getPrinterId()));
                                intent.putExtra("name", printerGets.get(position).getType());
                                intent.putExtra("data", printerGets.get(position));

                                Objects.requireNonNull(fragment.getActivity()).startActivity(intent);
                                return true;
                            case R.id.delete:
                                sharedPrefManager = new SharedPrefManager(view.getContext());
                                String token = sharedPrefManager.getSpToken();
                                Service service = Client.getClient().create(Service.class);
                                Call<DeletePrinterResponse> delete = service.deletePrinter("Bearer " + token, printerGets.get(position).getPrinterId(), printerGets.get(position).getFileId());

                                delete.enqueue(new Callback<DeletePrinterResponse>() {
                                    Response<DeletePrinterResponse> response;

                                    @Override
                                    public void onResponse(@NotNull Call<DeletePrinterResponse> call, @NotNull Response<DeletePrinterResponse> response) {

                                        this.response = response;
                                        if (response.isSuccessful()) {
                                            Toast.makeText(fragment.getActivity(), view.getContext().getString(R.string.msg_success_delete), Toast.LENGTH_SHORT).show();
                                            printerGets.remove(printerGets.get(position));
                                            notifyDataSetChanged();
                                            if (printerGets.size() == 0) {
                                                fragment.null_layout.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Toast.makeText(fragment.getActivity(), view.getContext().getString(R.string.msg_error), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<DeletePrinterResponse> call, @NotNull Throwable t) {

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
        return printerGets.size();
    }

    public class ViewHolderPrinter extends RecyclerView.ViewHolder {
        TextView nama, departement, more;

        public ViewHolderPrinter(@NonNull View v) {
            super(v);
            nama = v.findViewById(R.id.nama_printer);
            departement = v.findViewById(R.id.departement);
            more = v.findViewById(R.id.more_printer);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        DataPrinterItem data = printerGets.get(position);
                        Intent intent = new Intent(fragment.getActivity(), DetailPrinter.class);
                        intent.putExtra("printer", data);
                        Objects.requireNonNull(fragment.getActivity()).startActivity(intent);
                    }
                }
            });

        }

        void bind(DataPrinterItem printeritem) {
            nama.setText(printeritem.getType());
            departement.setText(printeritem.getDivisi());


        }
    }
}