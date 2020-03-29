package com.faresa.inventaris_ontrucks.ui.laptop.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetData;

public class DetailLaptop extends AppCompatActivity {
    TextView holder, type, os, sn, ic, divisi;
    TextView detail_image;
    CardView cardView;

    LaptopGetData laptopGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_laptop);
        laptopGetData = getIntent().getParcelableExtra("laptop");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(laptopGetData.getType());

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.transparent));


        divisi = findViewById(R.id.departement);
        holder = findViewById(R.id.holder);
        type = findViewById(R.id.type);
        os = findViewById(R.id.os);
        sn = findViewById(R.id.serial);
        ic = findViewById(R.id.invent);
        detail_image = findViewById(R.id.detail_file_image);
        cardView = findViewById(R.id.card_image);

        String divisiT = laptopGetData.getDivisi();
        String holderT = laptopGetData.getPIC();
        String typeT = laptopGetData.getType();
        String osT = laptopGetData.getOperatingSystem();
        String snT = laptopGetData.getSerialNumber();
        String icT = laptopGetData.getInventarisCode();
        String imageview = laptopGetData.getPath();
        Log.d("path", imageview);

        holder.setText(holderT);
        type.setText(typeT);
        os.setText(osT);
        sn.setText(snT);
        ic.setText(icT);
        divisi.setText(divisiT);
        detail_image.setText(laptopGetData.getPath());
        if (imageview.equals("kosong")) {
            cardView.setVisibility(View.GONE);
        } else {
            cardView.setVisibility(View.VISIBLE);
        }

        detail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailLaptop.this, PdfDetail.class);
                intent.putExtra("data", laptopGetData);
                startActivity(intent);
            }
        });


    }






}
