package com.faresa.inventaris_ontrucks.ui.divisi;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DivisiGetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisiViewModel extends AndroidViewModel {
    private MutableLiveData<DivisiGetResponse> getDivisi;
    private SharedPrefManager sharedPrefManager;

    public DivisiViewModel(@NonNull Application application) {
        super(application);
        sharedPrefManager = new SharedPrefManager(application);
    }


    public void loadEvent() {
        try {
            String token = sharedPrefManager.getSpToken();
            Service service = Client.getClient().create(Service.class);
            Call<DivisiGetResponse> eventCall = service.getDivision("Bearer " + token);
            eventCall.enqueue(new Callback<DivisiGetResponse>() {

                @Override
                public void onResponse(Call<DivisiGetResponse> call, Response<DivisiGetResponse> response) {

                    getDivisi.setValue(response.body());

                }

                @Override
                public void onFailure(Call<DivisiGetResponse> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }

    public LiveData<DivisiGetResponse> liveGet() {
        if (getDivisi == null) {
            getDivisi = new MutableLiveData<>();
            loadEvent();
        }
        return getDivisi;
    }
}
