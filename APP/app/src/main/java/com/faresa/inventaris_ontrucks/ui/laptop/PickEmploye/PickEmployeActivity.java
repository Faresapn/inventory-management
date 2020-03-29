package com.faresa.inventaris_ontrucks.ui.laptop.PickEmploye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiDataItem;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiGetResponse;
import com.faresa.inventaris_ontrucks.ui.pegawai.PegawaiViewModel;

import java.util.ArrayList;
import java.util.List;

public class PickEmployeActivity extends AppCompatActivity {
    private PegawaiViewModel pegawaiViewModel;
    private RecyclerView recyclerView;

    String type, os, serial, code;

    PickEmployeAdapter pickEmployeAdapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private ProgressBar pb;
    ConstraintLayout null_layout;
    List<PegawaiDataItem> pegawai = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_employe);

        type = getIntent().getStringExtra("type_extra");
        os = getIntent().getStringExtra("os_extra");
        serial = getIntent().getStringExtra("serial_extra");
        code = getIntent().getStringExtra("code_extra");



        pegawaiViewModel = ViewModelProviders.of(this).get(PegawaiViewModel.class);
        pickEmployeAdapter = new PickEmployeAdapter(this, this);
        pickEmployeAdapter.setPegawaiGets(pegawai);
        recyclerView = findViewById(R.id.rv);
        pb = findViewById(R.id.pb);
        null_layout = findViewById(R.id.null_image);
        initRV();
        getData();
    }

    private void getData() {


        pegawaiViewModel.pegawaiGet().observe(this, new Observer<PegawaiGetResponse>() {
            @Override
            public void onChanged(PegawaiGetResponse pegawaiGetResponse) {

                try {
                    pegawai.clear();
                    pegawai.addAll(pegawaiGetResponse.getData());
                    recyclerView.setAdapter(pickEmployeAdapter);
                    pickEmployeAdapter.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);
                    null_layout.setVisibility(View.GONE);
                } catch (Exception e) {
                    null_layout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initRV() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
}
