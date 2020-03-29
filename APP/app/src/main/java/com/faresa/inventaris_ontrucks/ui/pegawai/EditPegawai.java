package com.faresa.inventaris_ontrucks.ui.pegawai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.faresa.inventaris_ontrucks.BuildConfig;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiDataItem;
import com.faresa.inventaris_ontrucks.pojo.pegawai.update.PegawaiUpdateResponse;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class EditPegawai extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout etnama;
    ImageView imageView;
    Button edit, pick, editG, save, btn2;
    ProgressBar pb;

    PegawaiDataItem dataItem;
    Context mContext;
    TextView divisi;
    SharedPrefManager sharedPrefManager;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int CAMERA_PIC_REQUEST = 1;
    private Uri fileUri;
    ProgressDialog pDialog;
    String url = "https://pkl-api.adsmall.id/storage/file/pegawai/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pegawai);
        mContext = this;

        ActivityCompat.requestPermissions(EditPegawai.this,
                new String[]{Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);


        dataItem = getIntent().getParcelableExtra("data");
        etnama = findViewById(R.id.div_edit_pegawai);
        edit = findViewById(R.id.btn_edit_pegawai);
        save = findViewById(R.id.save);
        pb = findViewById(R.id.pb);
        editG = findViewById(R.id.btn_edit_galery);

        imageView = findViewById(R.id.previeww);
        pick = findViewById(R.id.updateImage);
        divisi = findViewById(R.id.textView13);

        divisi.setText(dataItem.getDivisiName());
        Objects.requireNonNull(divisi.getText());
        Objects.requireNonNull(etnama.getEditText()).setText(dataItem.getName());
        Glide.with(this).load(url + dataItem.getPath())
                .placeholder(R.drawable.ic_nodata).into(imageView);
        sharedPrefManager = new SharedPrefManager(this);
        editG.setOnClickListener(this);
        pick.setOnClickListener(this);
        edit.setOnClickListener(this);
        save.setOnClickListener(this);
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
        }
    }





    private void display(Spin spin) {
        String name = dataItem.getDivisiName();
        Integer ID = dataItem.getDivisiId();

        String userdata = " Nomor ID " + ID + " Divisi " + name;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.layott), userdata, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.updateImage:
                save.setVisibility(GONE);
                new MaterialDialog.Builder(this)
                        .title(R.string.uploadImages)
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                        editG.setVisibility(VISIBLE);
                                        edit.setVisibility(GONE);
                                        break;
                                    case 1:
                                        captureImage();
                                        edit.setVisibility(VISIBLE);
                                        editG.setVisibility(GONE);
                                        break;
                                    case 2:
                                        imageView.setImageResource(R.drawable.ic_launcher_background);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.btn_edit_pegawai:
                updateFile();
                break;
            case R.id.btn_edit_galery:
                uploadFilee();
                break;
            case R.id.save:
                upd();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {android.provider.MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();
                    fileUri = Uri.parse(mediaPath);

                }


            } else if (requestCode == CAMERA_PIC_REQUEST) {
                fileUri.toString();
                imageView.setImageURI(Uri.parse(fileUri.toString()));
            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo;
        try {
            photo = createTemporaryFile(this);
            fileUri = FileProvider.getUriForFile(Objects.requireNonNull(this),
                    BuildConfig.APPLICATION_ID + ".provider",
                    Objects.requireNonNull(photo));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_PIC_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File createTemporaryFile(Context context) {
        try {
            File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "prof_pic");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            return File.createTempFile("" + System.currentTimeMillis(), ".jpg", folder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    private void updateFile() {
        if (fileUri == null || fileUri.equals("")) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        } else {
            showpDialog();
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DCIM), "prof_pic/" + fileUri.getLastPathSegment());
            if ((file.length() / 1024) >= 1024) {
                Toast.makeText(this, "your file is big size", Toast.LENGTH_SHORT).show();
                hidepDialog();
            } else {
                Map<String, RequestBody> map = new HashMap<>();

                String spinners = Objects.requireNonNull(divisi.getText().toString());
                String nama = Objects.requireNonNull(etnama.getEditText()).getText().toString().trim();
                RequestBody requestput = RequestBody.create("PUT", MediaType.parse("multipart/form-data"));
                RequestBody requesFileId = RequestBody.create(String.valueOf(dataItem.getFileId()), MediaType.parse("multipart/form-data"));
                Log.d("asu", String.valueOf(dataItem.getFileId()));
                RequestBody requestname = RequestBody.create(nama, MediaType.parse("multipart/form-data"));
                String idData = String.valueOf(dataItem.getPegawaiId());
                RequestBody requestspin = RequestBody.create(spinners, MediaType.parse("multipart/form-data"));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
                String auth = sharedPrefManager.getSpToken();
                Service service = Client.getClient().create(Service.class);
                Call<PegawaiUpdateResponse> create = service.updatePegawai("Bearer " + auth, requestname, requestput, requestspin, requesFileId, map, idData);
                create.enqueue(new Callback<PegawaiUpdateResponse>() {
                    private Response<PegawaiUpdateResponse> response;

                    @Override
                    public void onResponse(Call<PegawaiUpdateResponse> call, Response<PegawaiUpdateResponse> response) {

                        this.response = response;
                        if (response.isSuccessful()) {
                            Toast.makeText(mContext, "Berhasil update", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, "Gagal update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PegawaiUpdateResponse> call, Throwable t) {
                        Log.e("failure", t.toString());
                    }

                });
            }
        }

    }

    private void uploadFilee() {
        if (fileUri == null || fileUri.equals("")) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        } else {
            showpDialog();
            File files = new File(String.valueOf(fileUri));
            if ((files.length() / 1024) >= 1024) {
                Toast.makeText(this, "your file is big size", Toast.LENGTH_SHORT).show();
                hidepDialog();
            } else {
                Map<String, RequestBody> map = new HashMap<>();
                String nama = Objects.requireNonNull(etnama.getEditText()).getText().toString().trim();
                /* RequestBody requestid = RequestBody.create(idData, MediaType.parse("multipart/form-data"));*/
                RequestBody requestput = RequestBody.create("PUT", MediaType.parse("multipart/form-data"));
                RequestBody requesFileId = RequestBody.create(String.valueOf(dataItem.getFileId()), MediaType.parse("multipart/form-data"));
                RequestBody requestname = RequestBody.create(nama, MediaType.parse("multipart/form-data"));
                String idData = String.valueOf(dataItem.getPegawaiId());
                RequestBody requestspin = RequestBody.create(divisi.getText().toString(), MediaType.parse("multipart/form-data"));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), files);
                map.put("file\"; filename=\"" + files.getName() + "\"", requestBody);
                String auth = sharedPrefManager.getSpToken();
                Service service = Client.getClient().create(Service.class);
                Call<PegawaiUpdateResponse> create = service.updatePegawai("Bearer " + auth, requestname, requestput, requestspin, requesFileId, map, idData);
                create.enqueue(new Callback<PegawaiUpdateResponse>() {
                    private Response<PegawaiUpdateResponse> response;

                    @Override
                    public void onResponse(Call<PegawaiUpdateResponse> call, Response<PegawaiUpdateResponse> response) {

                        this.response = response;
                        if (response.isSuccessful()) {
                            Toast.makeText(mContext, "Berhasil update", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, "Gagal update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PegawaiUpdateResponse> call, Throwable t) {
                        Log.e("failure", t.toString());
                    }

                });
            }
        }
    }

    private void upd() {


        showpDialog();

        String nama = Objects.requireNonNull(etnama.getEditText()).getText().toString().trim();
        /* RequestBody requestid = RequestBody.create(idData, MediaType.parse("multipart/form-data"));*/
        RequestBody requestput = RequestBody.create("PUT", MediaType.parse("multipart/form-data"));

        RequestBody requestname = RequestBody.create(nama, MediaType.parse("multipart/form-data"));
        String idData = String.valueOf(dataItem.getPegawaiId());
        RequestBody requestspin = RequestBody.create(divisi.getText().toString(), MediaType.parse("multipart/form-data"));
        String auth = sharedPrefManager.getSpToken();
        Service service = Client.getClient().create(Service.class);
        Call<PegawaiUpdateResponse> create = service.updatePegawaii("Bearer " + auth, requestname, requestput, requestspin, idData);
        create.enqueue(new Callback<PegawaiUpdateResponse>() {
            private Response<PegawaiUpdateResponse> response;

            @Override
            public void onResponse(Call<PegawaiUpdateResponse> call, Response<PegawaiUpdateResponse> response) {

                this.response = response;
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Berhasil update", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, "Gagal update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PegawaiUpdateResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }

        });
    }

    }


