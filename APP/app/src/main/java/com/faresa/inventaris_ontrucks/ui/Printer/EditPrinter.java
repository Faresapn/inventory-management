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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.faresa.inventaris_ontrucks.pojo.printer.get.DataPrinterItem;
import com.faresa.inventaris_ontrucks.pojo.printer.update.PrinterUpdateResponse;
import com.shockwave.pdfium.PdfDocument;

import org.jetbrains.annotations.NotNull;

import java.io.File;
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

public class EditPrinter extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {
    TextInputLayout etType, etCode;
    TextView placeholder;

    TextView divisi;
    Button pickPdf, upload;
    DataPrinterItem printerItem;
    private int pageNumber = 0;
    private String pdfFileName;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    SharedPrefManager sharedPrefManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_edit_printer);

        ActivityCompat.requestPermissions(EditPrinter.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        printerItem = getIntent().getParcelableExtra("data");
        divisi = findViewById(R.id.textView14);
        pdfView = findViewById(R.id.pdfView);
        pickPdf = findViewById(R.id.pickImage);
        upload = findViewById(R.id.upload);
        placeholder = findViewById(R.id.placeholder);
        etType = findViewById(R.id.detail_type);
        Objects.requireNonNull(etType.getEditText()).setText(printerItem.getType());

        etCode = findViewById(R.id.detail_code);
        Objects.requireNonNull(etCode.getEditText()).setText(printerItem.getInventarisCode());
        try {
            placeholder.setText(printerItem.getPath());
        } catch (Exception e) {
            placeholder.setText(getString(R.string.empty));
        }
        divisi.setText(printerItem.getDivisi());
        Objects.requireNonNull(divisi.getText());
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
        initDialog();


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


    private void initDialog() {

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
            assert path != null;
            File file = new File(path);
            displayFromFile(file);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
                placeholder.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
            } else {
                placeholder.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);

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

    public void resetPath()  // method
    {
        pdfPath = null;
    }
    private void uploadFile() {
        if (pdfPath == null) {


            String id = String.valueOf(printerItem.getPrinterId());
            RequestBody requestput = RequestBody.create("PUT", MediaType.parse("multipart/form-data"));
            String type = Objects.requireNonNull(etType.getEditText()).getText().toString().trim();
            String inv = Objects.requireNonNull(etCode.getEditText()).getText().toString().trim();
            RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));
            RequestBody requestInv = RequestBody.create(inv, MediaType.parse("multipart/form-data"));
            RequestBody requestspin = RequestBody.create(divisi.getText().toString(), MediaType.parse("multipart/form-data"));

            sharedPrefManager = new SharedPrefManager(getApplicationContext());
            String token = sharedPrefManager.getSpToken();
            Service service = Client.getClient().create(Service.class);
            Call<PrinterUpdateResponse> create = service.updatePrinterPDF("Bearer " + token, requestspin, requestType, requestInv, id, requestput);
            create.enqueue(new Callback<PrinterUpdateResponse>() {
                private Response<PrinterUpdateResponse> response;

                @Override
                public void onResponse(Call<PrinterUpdateResponse> call, Response<PrinterUpdateResponse> response) {

                    this.response = response;
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Berhasil Mengganti printer", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(mContext, "Gagal mengganti printer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PrinterUpdateResponse> call, Throwable t) {
                    Log.e("failure", t.toString());
                }
            });


        } else {
            // Map is used to multipart the file using okhttp3.RequestBody
            File file = new File(pdfPath);
            Map<String, RequestBody> map = new HashMap<>();


            String id = String.valueOf(printerItem.getPrinterId());
            RequestBody requestput = RequestBody.create("PUT", MediaType.parse("multipart/form-data"));
            String type = Objects.requireNonNull(etType.getEditText()).getText().toString().trim();
            String inv = Objects.requireNonNull(etCode.getEditText()).getText().toString().trim();
            RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));
            RequestBody requestInv = RequestBody.create(inv, MediaType.parse("multipart/form-data"));
            RequestBody requestspin = RequestBody.create(divisi.getText().toString(), MediaType.parse("multipart/form-data"));
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
            RequestBody requesFileId = RequestBody.create(String.valueOf(printerItem.getFileId()), MediaType.parse("multipart/form-data"));

            sharedPrefManager = new SharedPrefManager(getApplicationContext());
            String token = sharedPrefManager.getSpToken();
            Service service = Client.getClient().create(Service.class);
            Call<PrinterUpdateResponse> create = service.updatePrinter("Bearer " + token, requestspin, requestType, requestInv, map, requesFileId, id, requestput);
            create.enqueue(new Callback<PrinterUpdateResponse>() {
                private Response<PrinterUpdateResponse> response;

                @Override
                public void onResponse(Call<PrinterUpdateResponse> call, Response<PrinterUpdateResponse> response) {

                    this.response = response;
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Berhasil update Printer", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(mContext, "Gagal menambahkan ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PrinterUpdateResponse> call, Throwable t) {
                    Log.e("failure", t.toString());
                }
            });

        }
    }
}
