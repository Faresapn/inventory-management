package com.faresa.inventaris_ontrucks.pojo.pegawai.update;

import com.google.gson.annotations.SerializedName;

public class PegawaiUpdateResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("pegawaiDataUpdate")
    private PegawaiDataUpdate pegawaiDataUpdate;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setPegawaiDataUpdate(PegawaiDataUpdate pegawaiDataUpdate) {
        this.pegawaiDataUpdate = pegawaiDataUpdate;
    }

    public PegawaiDataUpdate getPegawaiDataUpdate() {
        return pegawaiDataUpdate;
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
                "PegawaiUpdateResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",pegawaiDataUpdate = '" + pegawaiDataUpdate + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}