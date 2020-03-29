package com.faresa.inventaris_ontrucks.ui.divisi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DataGet;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DivisiGetResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DivisiFragment extends Fragment {
    private RecyclerView recyclerView;
    private DivisiViewModel divisiViewModel;
    private DivisiAdapter divisiAdapter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    private ProgressBar pb;
    ConstraintLayout null_layout;
    private SharedPrefManager sharedPrefManager;

    private List<DataGet> divisi = new ArrayList<>();

    public DivisiFragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_divisi, container, false);
        divisiViewModel = ViewModelProviders.of(this).get(DivisiViewModel.class);
        divisiAdapter = new DivisiAdapter(this, sharedPrefManager);
        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));
        divisiAdapter.setDataGets(divisi);
        recyclerView = v.findViewById(R.id.rv);
        pb = v.findViewById(R.id.pb);
        null_layout = v.findViewById(R.id.null_image);
        initRV();
        getData();


        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DivisiCreate.class);
                startActivity(intent);
            }
        });

        pb.setVisibility(View.VISIBLE);
        return v;
    }

    private void getData() {
        divisiViewModel.liveGet().observe(getViewLifecycleOwner(), new Observer<DivisiGetResponse>() {
            @Override
            public void onChanged(DivisiGetResponse divisiGetResponse) {
                try {
                    divisi.clear();
                    divisi.addAll(divisiGetResponse.getData());
                    recyclerView.setAdapter(divisiAdapter);
                    divisiAdapter.notifyDataSetChanged();
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
        Log.d("iyaaaaaaaa", "LUMAYAN");
        divisiViewModel.loadEvent();

    }
}
