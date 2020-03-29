package com.faresa.inventaris_ontrucks.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.JumlahResponse;
import com.faresa.inventaris_ontrucks.pojo.admin.AdminResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    TextView td, te, tl, tp, tde, tee, tll, tpp;
    SharedPrefManager sharedPrefManager;
    private ProgressBar pb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        td = v.findViewById(R.id.jumlah_divisi);
        te = v.findViewById(R.id.jumlah_employe);
        tl = v.findViewById(R.id.jumlah_laptop);
        tp = v.findViewById(R.id.jumlah_printer);
        tde = v.findViewById(R.id.jumlah_division);
        tee = v.findViewById(R.id.jumlah_employee);
        tll = v.findViewById(R.id.jumlah_laptopion);
        tpp = v.findViewById(R.id.jumlah_printerion);

        View laptop = v.findViewById(R.id.imageView7);
        laptop.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_item_to_nav_laptop, null));


        View printer = v.findViewById(R.id.imageView8);
        printer.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_printer, null));

        pb = v.findViewById(R.id.pb);
        sharedPrefManager = new SharedPrefManager(getContext());
        final TextView userEmail = v.findViewById(R.id.nama_home);
        Service service = Client.getClient().create(Service.class);
        String token = sharedPrefManager.getSpToken();
        Call<AdminResponse> eventCall = service.getUser("Bearer " + token);
        eventCall.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                AdminResponse adminResponse = response.body();
                userEmail.setText(String.valueOf(adminResponse.getUser().getName()));

            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {

            }
        });


        init();
        inition();
        return v;


    }

    private void inition() {
        Service service = Client.getClient().create(Service.class);
        Call<JumlahResponse> eventCall = service.getAll();
        eventCall.enqueue(new Callback<JumlahResponse>() {
            private Response<JumlahResponse> response;

            @Override
            public void onResponse(Call<JumlahResponse> call, Response<JumlahResponse> response) {
                this.response = response;
                if (response.isSuccessful()) {


                    try {

                        tde.setText(String.valueOf(response.body().getData().getJumlahDivisi()));
                        tee.setText(String.valueOf(response.body().getData().getJumlahPegawai()));
                        tll.setText(String.valueOf(response.body().getData().getJumlahLaptop()));
                        tpp.setText(String.valueOf(response.body().getData().getJumlahPrinter()));
                        pb.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.d("exception", String.valueOf(e));
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<JumlahResponse> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });

    }

    private void init() {
        Service service = Client.getClient().create(Service.class);
        Call<JumlahResponse> eventCall = service.getAll();
        eventCall.enqueue(new Callback<JumlahResponse>() {
            private Response<JumlahResponse> response;

            @Override
            public void onResponse(Call<JumlahResponse> call, Response<JumlahResponse> response) {
                this.response = response;
                if (response.isSuccessful()) {


                    try {

                        td.setText(String.valueOf(response.body().getData().getJumlahDivisi()));
                        te.setText(String.valueOf(response.body().getData().getJumlahPegawai()));
                        tl.setText(String.valueOf(response.body().getData().getJumlahLaptop()));
                        tp.setText(String.valueOf(response.body().getData().getJumlahPrinter()));
                        pb.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.d("exception", String.valueOf(e));
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.msg_error1), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<JumlahResponse> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });

    }


}
