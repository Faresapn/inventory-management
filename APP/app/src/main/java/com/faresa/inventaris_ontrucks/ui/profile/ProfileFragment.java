package com.faresa.inventaris_ontrucks.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.activity.LoginActivity;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.admin.AdminResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private Button logout;
    private SharedPrefManager sharedPrefManager;
    private TextView nama, email, id, created;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(getContext());
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        nama = v.findViewById(R.id.prof_nama);
        email = v.findViewById(R.id.prof_email);
        id = v.findViewById(R.id.prof_id);
        created = v.findViewById(R.id.prof_created);
        Service service = Client.getClient().create(Service.class);
        String token = sharedPrefManager.getSpToken();
        Call<AdminResponse> eventCall = service.getUser("Bearer " + token);
        eventCall.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                AdminResponse adminResponse = response.body();
                assert adminResponse != null;
                nama.setText(adminResponse.getUser().getName());
                email.setText(adminResponse.getUser().getEmail());
                id.setText(String.valueOf(adminResponse.getUser().getUserId()));
                created.setText(adminResponse.getUser().getCreatedAt());
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {

            }
        });



        logout = v.findViewById(R.id.but_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(getActivity(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finishActivity();

            }
        });

        return v;
    }

    private void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}