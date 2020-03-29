package com.faresa.inventaris_ontrucks.ui.Printer.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.printer.get.DataPrinterItem;

public class DetailPrinter extends AppCompatActivity {
    TextView divisi, type, inv;
    ImageView detail_image;
    DataPrinterItem printerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_printer);
        printerItem = getIntent().getParcelableExtra("printer");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(printerItem.getType());

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.transparent));

        divisi = findViewById(R.id.holder);
        inv = findViewById(R.id.inv_code);
        type = findViewById(R.id.inv_type);
        detail_image = findViewById(R.id.detail_file_image);

        String divisiT = printerItem.getDivisi();
        String invT = printerItem.getInventarisCode();
        String typeT = printerItem.getType();
        String imageview = printerItem.getPath();
        Log.d("path", imageview);
        divisi.setText(divisiT);
        type.setText(typeT);
        inv.setText(invT);
        detail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailPrinter.this, PdfPrinter.class);
                intent.putExtra("data", printerItem);
                startActivity(intent);
            }
        });


    }

}
