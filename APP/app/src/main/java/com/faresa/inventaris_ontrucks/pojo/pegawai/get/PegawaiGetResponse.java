package com.faresa.inventaris_ontrucks.pojo.pegawai.get;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PegawaiGetResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("data")
    private List<PegawaiDataItem> data;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setData(List<PegawaiDataItem> data) {
        this.data = data;
    }

    public List<PegawaiDataItem> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "PegawaiGetResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}