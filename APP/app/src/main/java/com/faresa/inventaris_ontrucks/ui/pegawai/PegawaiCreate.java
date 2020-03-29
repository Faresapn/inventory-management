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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.faresa.inventaris_ontrucks.BuildConfig;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;

import com.faresa.inventaris_ontrucks.pojo.divisi.get.DataGet;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DivisiGetResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.create.PegawaiCreateResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiDataItem;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PegawaiCreate extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout etNama;
    PegawaiDataItem dataGet;
    ProgressBar pb;
    Spinner spinner;
    ImageView imageView;
    Button pick, submit,submitG,submitnull;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int CAMERA_PIC_REQUEST = 1;
    private Uri fileUri;
    int hold;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pegawai);
        mContext = this;
        etNama = findViewById(R.id.div_pegawai);
        submit = findViewById(R.id.btn_submit);
        pb = findViewById(R.id.pb);
        spinner = findViewById(R.id.spinner);
        submitG = findViewById(R.id.galery);
        submitnull = findViewById(R.id.onestep);
        imageView = findViewById(R.id.previeww);
        pick = findViewById(R.id.getImage);
        dataGet = getIntent().getParcelableExtra("id");

        ActivityCompat.requestPermissions(PegawaiCreate.this,
                new String[]{Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        pick.setOnClickListener(this);
        submit.setOnClickListener(this);
        submitG.setOnClickListener(this);
        initDialog();
        init();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spin spin = (Spin) parent.getSelectedItem();
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
                }
                return;
            }
        }
    }
    private boolean validateName() {
        String email = (etNama.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            etNama.setError("Nama tidak boleh kosong");
            return false;
        }  else {
            etNama.setError(null);
            return true;
        }
    }
    private static File createTemporaryFile(Context context) {
        try {
            File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "prof_pic");
            if (!folder.exists()) {
                folder.mkdirs();
                Log.d("anji", String.valueOf(folder.mkdirs()));
            }
            return File.createTempFile("" + System.currentTimeMillis(), ".jpg", folder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

                    List<Spin> listSpinner = new ArrayList<Spin>();
                    for (int i = 0; i < items.size(); i++) {
                        Spin list = new Spin(items.get(i).getDivisiId(), items.get(i).getName());
                        listSpinner.add(list);
                        Log.d("list", String.valueOf(list));

                    }
                    ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(mContext,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                } else {
                    Toast.makeText(mContext, "Tambahkan Divisi Dahulu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DivisiGetResponse> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });


    }

    public void getSelected(View v) {
        Spin spin = (Spin) spinner.getSelectedItem();
        display(spin);
    }

    private void display(Spin spin) {
        String name = spin.getName();
        Integer ID = spin.getId();

        String userdata = " Nomor ID " + ID + " Divisi " + name;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.layot), userdata, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.getImage:
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
                                        submitnull.setVisibility(GONE);
                                        submitG.setVisibility(VISIBLE);
                                        submit.setVisibility(GONE);
                                        break;
                                    case 1:
                                        captureImage();
                                        submitnull.setVisibility(GONE);
                                        submit.setVisibility(VISIBLE);
                                        submitG.setVisibility(GONE);
                                        break;
                                    case 2:
                                        imageView.setImageResource(R.drawable.ic_launcher_background);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.btn_submit:
                uploadFile();
                break;
            case R.id.galery:
                uploadFilee();
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    private void uploadFile() {
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
                if (validateName()) {
                    hidepDialog();
                    return;

                } else {

                    Map<String, RequestBody> map = new HashMap<>();
                    String nama = Objects.requireNonNull(etNama.getEditText()).getText().toString().trim();
                    RequestBody requestname = RequestBody.create(nama, MediaType.parse("multipart/form-data"));
                    RequestBody requestspin = RequestBody.create(spinner.getSelectedItem().toString(), MediaType.parse("multipart/form-data"));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(" "), file);
                    map.put("file\"; filename=\"" + file + "\"", requestBody);
                    sharedPrefManager = new SharedPrefManager(getApplicationContext());
                    String token = sharedPrefManager.getSpToken();
                    Service service = Client.getClient().create(Service.class);
                    Call<PegawaiCreateResponse> create = service.createPegawai("Bearer " + token, requestname, requestspin, map);
                    create.enqueue(new Callback<PegawaiCreateResponse>() {
                        private Response<PegawaiCreateResponse> response;

                        @Override
                        public void onResponse(Call<PegawaiCreateResponse> call, Response<PegawaiCreateResponse> response) {

                            this.response = response;
                            if (response.isSuccessful()) {
                                Toast.makeText(mContext, "Berhasil menambahkan anggota", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(mContext, "Gagal menambahkan data matkul", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PegawaiCreateResponse> call, Throwable t) {
                            Log.e("failure", t.toString());
                        }
                    });

                }
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
                if (validateName()) {
                    hidepDialog();
                    return;
                } else{

                    Map<String, RequestBody> map = new HashMap<>();

                    String nama = Objects.requireNonNull(etNama.getEditText()).getText().toString().trim();
                    RequestBody requestname = RequestBody.create(nama, MediaType.parse("multipart/form-data"));
                    RequestBody requestspin = RequestBody.create(spinner.getSelectedItem().toString(), MediaType.parse("multipart/form-data"));
                    RequestBody requestBody = RequestBody.create(MediaType.parse(" "), files);
                    map.put("file\"; filename=\"" + files + "\"", requestBody);

                    sharedPrefManager = new SharedPrefManager(getApplicationContext());
                    String token = sharedPrefManager.getSpToken();
                    Service service = Client.getClient().create(Service.class);
                    Call<PegawaiCreateResponse> create = service.createPegawai("Bearer " + token, requestname, requestspin, map);
                    create.enqueue(new Callback<PegawaiCreateResponse>() {
                        private Response<PegawaiCreateResponse> response;

                        @Override
                        public void onResponse(Call<PegawaiCreateResponse> call, Response<PegawaiCreateResponse> response) {

                            this.response = response;
                            if (response.isSuccessful()) {
                                Toast.makeText(mContext, "Berhasil menambahkan anggota", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(mContext, "Gagal menambahkan data matkul", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PegawaiCreateResponse> call, Throwable t) {
                            Log.e("failure", t.toString());
                        }
                    });

                }

            }
        }

    }

}
