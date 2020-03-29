package com.faresa.inventaris_ontrucks.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.TokenResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    TextInputLayout etEmail;
    TextInputLayout etPassword;
    Button btnLogin;
    ProgressDialog loading;
    Context mContext = this;
    Service mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mApiService = Client.getClient().create(Service.class);


        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        initComponents();

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
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

    private void initComponents(){
        etEmail = findViewById(R.id.etEmail);
        etPassword =  findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()) {
                    return;
                }
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();


            }
        });

    }
    private boolean validateEmail() {
        String email = (etEmail.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email tidak boleh kosong");
            return false;
        }  else {
            etEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pw = etPassword.getEditText().getText().toString().trim();

        if (pw.isEmpty()) {
            etPassword.setError("Password tidak boleh kosong");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    private void requestLogin(){
        mApiService.loginRequest(etEmail.getEditText().getText().toString(), etPassword.getEditText().getText().toString())
                .enqueue(new Callback<TokenResponse>()
                {

                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            TokenResponse tokenResponse = response.body();
                            Log.d("tes", tokenResponse.getToken());

                            sharedPrefManager.saveToken(SharedPrefManager.SP_TOKEN,tokenResponse.getToken());
                            sharedPrefManager.getSpToken();
                            Log.d("token", sharedPrefManager.getSpToken());


                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(mContext);

                            dlgAlert.setMessage("Mohon Periksa Kembali");
                            dlgAlert.setTitle("Email Atau Password Anda Salah");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();


                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}
