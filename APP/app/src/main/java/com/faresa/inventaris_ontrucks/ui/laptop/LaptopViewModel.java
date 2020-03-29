package com.faresa.inventaris_ontrucks.ui.laptop;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaptopViewModel extends AndroidViewModel {
    private MutableLiveData<LaptopGetResponse> getLaptop;
    private SharedPrefManager sharedPrefManager;

    public LaptopViewModel(@NonNull Application application) {
        super(application);
        sharedPrefManager = new SharedPrefManager(application);
    }

    void loadEvent() {
        String token = sharedPrefManager.getSpToken();
        Service service = Client.getClient().create(Service.class);
        Call<LaptopGetResponse> call = service.getLaptop("Bearer " + token);
        call.enqueue(new Callback<LaptopGetResponse>() {

            @Override
            public void onResponse(@NotNull Call<LaptopGetResponse> call, @NotNull Response<LaptopGetResponse> response) {
                getLaptop.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<LaptopGetResponse> call, @NotNull Throwable t) {
                Log.e("failure", t.toString());

            }
        });
    }

    LiveData<LaptopGetResponse> liveGet() {
        if (getLaptop == null) {
            getLaptop = new MutableLiveData<>();
            loadEvent();
        }
        return getLaptop;
    }
}