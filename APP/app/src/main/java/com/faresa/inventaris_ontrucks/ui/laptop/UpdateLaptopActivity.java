package com.faresa.inventaris_ontrucks.ui.laptop;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetData;
import com.faresa.inventaris_ontrucks.pojo.laptop.update.LaptopUpdateResponse;
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

public class UpdateLaptopActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    TextInputLayout etType, etOs, etSerial, etCode;
    TextView holder, placeholder;
    Button pickPdf, upload;
    LaptopGetData laptopGetData;

    private int pageNumber = 0;


    private String pdfFileName;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_laptop);
        laptopGetData = getIntent().getParcelableExtra("data");
        pdfView = findViewById(R.id.pdfView);
        pickPdf = findViewById(R.id.pickImage);
        upload = findViewById(R.id.upload);
        placeholder = findViewById(R.id.placeholder);

        ActivityCompat.requestPermissions(UpdateLaptopActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        try {
            holder = findViewById(R.id.parceHolder);
            holder.setText(laptopGetData.getPIC());
        } catch (Exception e) {
            holder.setText(getString(R.string.placeholder_nama));

        }
        try {
            placeholder.setText(laptopGetData.getPath());
        } catch (Exception e) {
            placeholder.setText(getString(R.string.empty));
        }

        etType = findViewById(R.id.detail_type);
        Objects.requireNonNull(etType.getEditText()).setText(laptopGetData.getType());

        etOs = findViewById(R.id.detail_os);
        Objects.requireNonNull(etOs.getEditText()).setText(laptopGetData.getOperatingSystem());

        etSerial = findViewById(R.id.detail_serial);
        Objects.requireNonNull(etSerial.getEditText()).setText(laptopGetData.getSerialNumber());

        etCode = findViewById(R.id.detail_code);
        Objects.requireNonNull(etCode.getEditText()).setText(laptopGetData.getInventarisCode());



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
        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //granted
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.pickFile:
//                launchPicker();
//                return true;
//            case R.id.upload:
//                uploadFile();
//                return true;
//        }
//
//        return (super.onOptionsItemSelected(item));
//    }

    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle(getString(R.string.select_pdf))
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
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
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

    private void uploadFile() {
        try {
            if (pdfPath == null) {
                showpDialog();

                // Map is used to multipart the file using okhttp3.RequestBody
                sharedPrefManager = new SharedPrefManager(getApplicationContext());
                String token = sharedPrefManager.getSpToken();


                String id = String.valueOf(laptopGetData.getLaptopId());
                String type = Objects.requireNonNull(etType.getEditText()).getText().toString().trim();
                String os = Objects.requireNonNull(etOs.getEditText()).getText().toString().trim();
                String serial = Objects.requireNonNull(etSerial.getEditText()).getText().toString().trim();
                String code = Objects.requireNonNull(etCode.getEditText()).getText().toString().trim();
                String put = "PUT";

                RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));
                RequestBody requestOs = RequestBody.create(os, MediaType.parse("multipart/form-data"));
                RequestBody requestSerial = RequestBody.create(serial, MediaType.parse("multipart/form-data"));
                RequestBody requestCode = RequestBody.create(code, MediaType.parse("multipart/form-data"));
                RequestBody requestPut = RequestBody.create("PUT", MediaType.parse("multipart/form-data"));
                Log.d("wong asu 2", String.valueOf(requestPut));


                Service service = Client.getClient().create(Service.class);
                Call<LaptopUpdateResponse> call = service.updateLaptop2("Bearer " + token, id, requestType, requestSerial, requestCode, requestOs, requestPut);
                call.enqueue(new Callback<LaptopUpdateResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LaptopUpdateResponse> call, @NotNull Response<LaptopUpdateResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                hidepDialog();
                                LaptopUpdateResponse serverResponse = response.body();
                                Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("123data", serverResponse.getMessage());
                                finish();

                            }
                        } else {
                            hidepDialog();
                            Toast.makeText(getApplicationContext(), getString(R.string.problem_pdf), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LaptopUpdateResponse> call, @NotNull Throwable t) {
                        hidepDialog();
                        Log.v("Response gotten is", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(getApplicationContext(), getString(R.string.failure_pdf) + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            } else {


                showpDialog();

                // Map is used to multipart the file using okhttp3.RequestBody
                sharedPrefManager = new SharedPrefManager(getApplicationContext());
                String token = sharedPrefManager.getSpToken();


                Map<String, RequestBody> map = new HashMap<>();
                File file = new File(pdfPath);
                // Parsing any Media type file
                String id = String.valueOf(laptopGetData.getLaptopId());
                Log.d("idd", id);
                String type = Objects.requireNonNull(etType.getEditText()).getText().toString().trim();
                String os = Objects.requireNonNull(etOs.getEditText()).getText().toString().trim();
                String serial = Objects.requireNonNull(etSerial.getEditText()).getText().toString().trim();
                String code = Objects.requireNonNull(etCode.getEditText()).getText().toString().trim();
                String put = "PUT";

                RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));
                RequestBody requestOs = RequestBody.create(os, MediaType.parse("multipart/form-data"));
                RequestBody requestSerial = RequestBody.create(serial, MediaType.parse("multipart/form-data"));
                RequestBody requestCode = RequestBody.create(code, MediaType.parse("multipart/form-data"));
                RequestBody requestPut = RequestBody.create(put, MediaType.parse("multipart/form-data"));
                RequestBody requesFileId = RequestBody.create(String.valueOf(laptopGetData.getFileId()), MediaType.parse("multipart/form-data"));
//            Log.d("asww", String.valueOf(requestPut));
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
                map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
                Service service = Client.getClient().create(Service.class);
                Call<LaptopUpdateResponse> call = service.updateLaptop("Bearer " + token, id, requestType, requestSerial, requestCode, requestOs, requesFileId, map, requestPut);
                call.enqueue(new Callback<LaptopUpdateResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LaptopUpdateResponse> call, @NotNull Response<LaptopUpdateResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                hidepDialog();
                                LaptopUpdateResponse serverResponse = response.body();
                                Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("123", serverResponse.getMessage());
                                finish();

                            }
                        } else {
                            hidepDialog();
                            Toast.makeText(getApplicationContext(), getString(R.string.problem_pdf), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LaptopUpdateResponse> call, @NotNull Throwable t) {
                        hidepDialog();
                        Log.v("Response gotten is", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(getApplicationContext(), getString(R.string.failure_pdf) + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

        } catch (Exception e) {
            hidepDialog();
            Toast.makeText(this, "please select Employee ", Toast.LENGTH_LONG).show();
        }
//        if (id == null){
//            Toast.makeText(this, "please select Employee ", Toast.LENGTH_LONG).show();
//            return;
//        }

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


}


