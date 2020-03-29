package com.faresa.inventaris_ontrucks.ui.divisi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.divisi.create.DivisiCreateResponse;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DataGet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisiCreate extends AppCompatActivity {
    TextInputLayout etNama;
    Button submit;
    DataGet dataGet;
    ProgressBar pb;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_div_laptop);

        etNama = findViewById(R.id.div_laptop);
        submit = findViewById(R.id.btn_submit);
        pb = findViewById(R.id.pb);
        dataGet = getIntent().getParcelableExtra("id");
        sharedPrefManager = new SharedPrefManager(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String nama = etNama.getEditText().getText().toString().trim();
                String token = sharedPrefManager.getSpToken();

                Service service = Client.getClient().create(Service.class);

                Call<DivisiCreateResponse> create = service.createDivision("Bearer " + token, nama);
                create.enqueue(new Callback<DivisiCreateResponse>() {
                    @Override
                    public void onResponse(Call<DivisiCreateResponse> call, Response<DivisiCreateResponse> response) {
                        try {
                            String kode = response.body().getStatusCode();
                            if (kode.equals("0001")) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(DivisiCreate.this, getString(R.string.msg_success), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(DivisiCreate.this, getString(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                Log.d("iya22", "ora");
                            }
                            finish();
                        } catch (Exception e) {
                            pb.setVisibility(View.GONE);
                            Log.d("iya", "ora");
                            Toast.makeText(DivisiCreate.this, getString(R.string.msg_duplicate), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<DivisiCreateResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
