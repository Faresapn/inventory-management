package com.faresa.inventaris_ontrucks.ui.Printer;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.printer.get.DataPrinterItem;
import com.faresa.inventaris_ontrucks.pojo.printer.get.PrinterGetResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrinterFragment extends Fragment {


    public PrinterFragment() {
        // Required empty public constructor
    }
    private PrinterViewModel printeriViewModel;
    private RecyclerView recyclerView;

    PrinterAdapter printerAdapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    private ProgressBar pb;
    ConstraintLayout null_layout;
    List<DataPrinterItem> printer = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_printer, container, false);
        printeriViewModel = ViewModelProviders.of(this).get(PrinterViewModel.class);
        printerAdapter = new PrinterAdapter(this);
        printerAdapter.setPrinterGets(printer);
        recyclerView = v.findViewById(R.id.rv_printer);
        pb = v.findViewById(R.id.pb);
        null_layout = v.findViewById(R.id.null_image);
        initRV();
        getData();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrinterCreate.class);
                startActivity(intent);
            }
        });
        pb.setVisibility(View.VISIBLE);
        return v;
    }

    private void getData() {


        printeriViewModel.printerGet().observe(this, new Observer<PrinterGetResponse>() {
            @Override
            public void onChanged(PrinterGetResponse printerGetResponse) {

                try {
                    printer.clear();
                    printer.addAll(printerGetResponse.getData());
                    recyclerView.setAdapter(printerAdapter);
                    printerAdapter.notifyDataSetChanged();
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
        pb.setVisibility(View.VISIBLE);
        super.onResume();
        printeriViewModel.loadPrinter();

    }

}
