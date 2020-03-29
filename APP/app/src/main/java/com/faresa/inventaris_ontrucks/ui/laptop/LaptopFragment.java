package com.faresa.inventaris_ontrucks.ui.laptop;

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
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetData;
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaptopFragment extends Fragment {
    private RecyclerView recyclerView;
    private LaptopViewModel laptopViewModel;
    private LaptopAdapter laptopAdapter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    private ProgressBar pb;
    public ConstraintLayout null_layout;
    private SharedPrefManager sharedPrefManager;

    private List<LaptopGetData> divisi = new ArrayList<>();

    public LaptopFragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_laptop, container, false);
        laptopViewModel = ViewModelProviders.of(this).get(LaptopViewModel.class);
        laptopAdapter = new LaptopAdapter(this, sharedPrefManager);
        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));
        laptopAdapter.setDataGets(divisi);
        recyclerView = v.findViewById(R.id.rv);
        pb = v.findViewById(R.id.pb);
        null_layout = v.findViewById(R.id.null_image);
        initRV();
        getData();


        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateLaptopActivity.class);
                startActivity(intent);
            }
        });

        pb.setVisibility(View.VISIBLE);
        return v;
    }

    private void getData() {


        laptopViewModel.liveGet().observe(getViewLifecycleOwner(), new Observer<LaptopGetResponse>() {
            @Override
            public void onChanged(LaptopGetResponse divisiGetResponse) {

                try {
                    divisi.clear();
                    divisi.addAll(divisiGetResponse.getData());
                    recyclerView.setAdapter(laptopAdapter);
                    laptopAdapter.notifyDataSetChanged();
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
        Log.d("oraora", "LUMAYAN2");
        laptopViewModel.loadEvent();

    }
}