package com.faresa.inventaris_ontrucks.ui.Printer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.printer.get.PrinterGetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrinterViewModel extends AndroidViewModel {
    private MutableLiveData<PrinterGetResponse> getPrinter;
    SharedPrefManager sharedPrefManager;

    public PrinterViewModel(@NonNull Application application) {
        super(application);
        sharedPrefManager = new SharedPrefManager(application);
    }

    public void loadPrinter() {
        String auth = sharedPrefManager.getSpToken();
        Service service = Client.getClient().create(Service.class);
        Call<PrinterGetResponse> eventCall = service.getPrinter("Bearer " + auth);
        eventCall.enqueue(new Callback<PrinterGetResponse>() {

            private Response<PrinterGetResponse> response;

            @Override
            public void onResponse(Call<PrinterGetResponse> call, Response<PrinterGetResponse> response) {
                this.response = response;
                getPrinter.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PrinterGetResponse> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });
    }

    public LiveData<PrinterGetResponse> printerGet() {
        if (getPrinter == null) {
            getPrinter = new MutableLiveData<>();
            loadPrinter();
        }
        return getPrinter;
    }
}
