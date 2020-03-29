package com.faresa.inventaris_ontrucks.ui.Printer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.textfield.TextInputLayout;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DataGet;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DivisiGetResponse;
import com.faresa.inventaris_ontrucks.pojo.printer.create.PrinterCreateResponse;
import com.shockwave.pdfium.PdfDocument;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrinterCreate extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {
    TextInputLayout etType, etCode;
    Context mContext;
    Button pickPdf, upload, removePdf;
    Spinner spinner;
    private int pageNumber = 0;
    private String pdfFileName;
    LinearLayout pdf;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_create);

        ActivityCompat.requestPermissions(PrinterCreate.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        mContext = this;
        pdfView = findViewById(R.id.pdfView);
        pickPdf = findViewById(R.id.pickImage);
        upload = findViewById(R.id.upload);
        etType = findViewById(R.id.detail_type);
        etCode = findViewById(R.id.detail_code);
        spinner = findViewById(R.id.spinner_printer);
        pdf = findViewById(R.id.pdf);
        removePdf = findViewById(R.id.removePdf);

        pickPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPicker();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        removePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPath();
                Toast.makeText(getApplicationContext(), "PDF Removed", Toast.LENGTH_LONG).show();
            }
        });

        initDialog();
        init();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinPrinter spin = (SpinPrinter) parent.getSelectedItem();
                display(spin);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {

                    // Denied
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Select PDF file")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
            displayFromFile(file);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
            }
        }

    }

    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);

        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(this);
        String auth = sharedPrefManager.getSpToken();
        Service service = Client.getClient().create(Service.class);
        Call<DivisiGetResponse> eventCall = service.getDivision("Bearer " + auth);
        eventCall.enqueue(new Callback<DivisiGetResponse>() {
            private Response<DivisiGetResponse> response;

            @Override
            public void onResponse(Call<DivisiGetResponse> call, Response<DivisiGetResponse> response) {
                this.response = response;
                if (response.isSuccessful()) {
                    List<DataGet> items = response.body().getData();
                    List<SpinPrinter> listSpinner = new ArrayList<SpinPrinter>();
                    for (int i = 0; i < items.size(); i++) {
                        SpinPrinter list = new SpinPrinter(items.get(i).getDivisiId(), items.get(i).getName());
                        listSpinner.add(list);
                        Log.d("list", String.valueOf(list));

                    }
                    ArrayAdapter<SpinPrinter> adapter = new ArrayAdapter<SpinPrinter>(mContext,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else {
                    Toast.makeText(mContext, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DivisiGetResponse> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });


    }

    public void getSelected(View v) {
        SpinPrinter spin = (SpinPrinter) spinner.getSelectedItem();
        display(spin);
    }

    private void display(SpinPrinter spin) {
        String name = spin.getName();
        Integer ID = spin.getId();

    }


    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(false);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    public void resetPath()  // method
    {
        pdfPath = null;
        pdf.setVisibility(View.GONE);
    }

    private void uploadFile() {
        if (pdfPath == null) {
            showpDialog();
            // Map is used to multipart the file using okhttp3.RequestBody
            String type = Objects.requireNonNull(etType.getEditText()).getText().toString().trim();
            String inv = Objects.requireNonNull(etCode.getEditText()).getText().toString().trim();
            RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));
            RequestBody requestInv = RequestBody.create(inv, MediaType.parse("multipart/form-data"));
            RequestBody requestspin = RequestBody.create(spinner.getSelectedItem().toString(), MediaType.parse("multipart/form-data"));


            sharedPrefManager = new SharedPrefManager(getApplicationContext());
            String token = sharedPrefManager.getSpToken();
            Service service = Client.getClient().create(Service.class);
            Call<PrinterCreateResponse> create = service.createPrinterNull("Bearer " + token, requestspin, requestType, requestInv);
            create.enqueue(new Callback<PrinterCreateResponse>() {
                private Response<PrinterCreateResponse> response;

                @Override
                public void onResponse(Call<PrinterCreateResponse> call, Response<PrinterCreateResponse> response) {

                    this.response = response;
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Berhasil menambahkan anggota", Toast.LENGTH_SHORT).show();
                        hidepDialog();
                        finish();
                    } else {
                        hidepDialog();
                        Toast.makeText(mContext, "Gagal menambahkan data matkul", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PrinterCreateResponse> call, Throwable t) {
                    Log.e("failure", t.toString());
                    hidepDialog();
                }
            });


        } else {
            showpDialog();
            // Map is used to multipart the file using okhttp3.RequestBody
            File file = new File(pdfPath);
            Map<String, RequestBody> map = new HashMap<>();
            String type = Objects.requireNonNull(etType.getEditText()).getText().toString().trim();
            String inv = Objects.requireNonNull(etCode.getEditText()).getText().toString().trim();
            RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));
            RequestBody requestInv = RequestBody.create(inv, MediaType.parse("multipart/form-data"));
            RequestBody requestspin = RequestBody.create(spinner.getSelectedItem().toString(), MediaType.parse("multipart/form-data"));
            RequestBody requestBody = RequestBody.create(MediaType.parse("pdf"), file);
            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);


            sharedPrefManager = new SharedPrefManager(getApplicationContext());
            String token = sharedPrefManager.getSpToken();
            Service service = Client.getClient().create(Service.class);
            Call<PrinterCreateResponse> create = service.createPrinter("Bearer " + token, requestspin, requestType, requestInv, map);
            create.enqueue(new Callback<PrinterCreateResponse>() {
                private Response<PrinterCreateResponse> response;

                @Override
                public void onResponse(Call<PrinterCreateResponse> call, Response<PrinterCreateResponse> response) {

                    this.response = response;
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Berhasil menambahkan anggota", Toast.LENGTH_SHORT).show();
                        hidepDialog();
                        finish();
                    } else {
                        hidepDialog();
                        Toast.makeText(mContext, "Gagal menambahkan data matkul", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PrinterCreateResponse> call, Throwable t) {
                    Log.e("failure", t.toString());
                    hidepDialog();
                }
            });

        }
    }


}
