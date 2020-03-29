package com.faresa.inventaris_ontrucks.ui.pegawai;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiGetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PegawaiViewModel extends AndroidViewModel {
    private MutableLiveData<PegawaiGetResponse> getPegawai;
    SharedPrefManager sharedPrefManager;

    public PegawaiViewModel(@NonNull Application application) {
        super(application);
        sharedPrefManager = new SharedPrefManager(application);
    }


    public void loadEvent() {
        String auth = sharedPrefManager.getSpToken();
        Service service = Client.getClient().create(Service.class);
        Call<PegawaiGetResponse> eventCall = service.getPegawai("Bearer " + auth);
        eventCall.enqueue(new Callback<PegawaiGetResponse>() {

            private Response<PegawaiGetResponse> response;
            @Override
            public void onResponse(Call<PegawaiGetResponse> call, Response<PegawaiGetResponse> response) {
                this.response = response;
                getPegawai.setValue(response.body());

            }

            @Override
            public void onFailure(Call<PegawaiGetResponse> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });
    }

    public LiveData<PegawaiGetResponse> pegawaiGet() {
        if (getPegawai == null) {
            getPegawai = new MutableLiveData<>();
            loadEvent();
        }
        return getPegawai;
    }
}
