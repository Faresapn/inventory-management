package com.faresa.inventaris_ontrucks.ui.laptop.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetData;

public class PdfDetail extends AppCompatActivity {
    LaptopGetData laptopGetData;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        laptopGetData = getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_pdf_detail);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        Log.d("idd", laptopGetData.getLaptopId() + laptopGetData.getPath() + laptopGetData.getPegawaiId());
        WebView web_view = findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
        String urlImage = laptopGetData.getPath();
        String myPdfUrl = "https://pkl-api.adsmall.id/storage/file/laptop/" + urlImage;
        String url = "https://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });

    }
}
