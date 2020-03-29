package com.faresa.inventaris_ontrucks.ui.divisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.divisi.update.DivisiUpdateResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisiEdit extends AppCompatActivity {
    TextInputLayout nama;
    Button edit;
    ProgressBar pb;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisi_edit);

        nama = findViewById(R.id.editDivision);
        edit = findViewById(R.id.btn_edit);
        pb = findViewById(R.id.pb);
        Intent data = getIntent();
        final String idData = data.getStringExtra("id");
        final String name = data.getStringExtra("name");
        Objects.requireNonNull(nama.getEditText()).setText(name);
        sharedPrefManager = new SharedPrefManager(this);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = sharedPrefManager.getSpToken();
                Service service = Client.getClient().create(Service.class);
                Call<DivisiUpdateResponse> update = service.updateDivision("Bearer " + token, nama.getEditText().getText().toString(), "PUT", idData);
                update.enqueue(new Callback<DivisiUpdateResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DivisiUpdateResponse> call, @NotNull Response<DivisiUpdateResponse> response) {
                        assert response.body() != null;
                        try {
                            String kode = response.body().getStatusCode();
                            Log.d("kode", response.body().getStatusCode());
                            if (kode.equals("0001")) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(DivisiEdit.this, getString(R.string.msg_success), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(DivisiEdit.this, getString(R.string.msg_error), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Log.d("iya", "ora");
                            Toast.makeText(DivisiEdit.this, getString(R.string.msg_duplicate), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<DivisiUpdateResponse> call, @NotNull Throwable t) {
                        Log.d("gaje", "asw");
                    }
                });
            }
        });


    }
}
