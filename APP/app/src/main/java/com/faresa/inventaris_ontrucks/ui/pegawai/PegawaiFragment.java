package com.faresa.inventaris_ontrucks.ui.pegawai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiDataItem;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiGetResponse;

import java.util.ArrayList;
import java.util.List;

public class PegawaiFragment extends Fragment {

    private PegawaiViewModel pegawaiViewModel;
    private RecyclerView recyclerView;

    PegawaiAdapter pegawaiAdapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    private ProgressBar pb;
    ConstraintLayout null_layout;
    List<PegawaiDataItem> pegawai = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pegawai, container, false);
        pegawaiViewModel = ViewModelProviders.of(this).get(PegawaiViewModel.class);
        pegawaiAdapter = new PegawaiAdapter(this);
        pegawaiAdapter.setPegawaiGets(pegawai);
        pegawaiAdapter.notifyDataSetChanged();
        recyclerView = v.findViewById(R.id.rv_);
        pb = v.findViewById(R.id.pb);
        null_layout = v.findViewById(R.id.null_image);
        initRV();
        getData();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PegawaiCreate.class);
                startActivity(intent);
            }
        });
        pb.setVisibility(View.VISIBLE);
        return v;
    }

    private void getData() {


        pegawaiViewModel.pegawaiGet().observe(getViewLifecycleOwner(), new Observer<PegawaiGetResponse>() {
            @Override
            public void onChanged(PegawaiGetResponse pegawaiGetResponse) {

                try {
                    pegawai.clear();
                    pegawai.addAll(pegawaiGetResponse.getData());
                    recyclerView.setAdapter(pegawaiAdapter);
                    pegawaiAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        pb.setVisibility(View.VISIBLE);
        pegawaiViewModel.loadEvent();

    }

}