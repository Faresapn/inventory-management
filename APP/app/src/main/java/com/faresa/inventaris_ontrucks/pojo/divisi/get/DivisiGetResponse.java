package com.faresa.inventaris_ontrucks.pojo.divisi.get;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DivisiGetResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("data")
    private List<DataGet> data;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setData(List<DataGet> data) {
        this.data = data;
    }

    public List<DataGet> getData() {
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
                "DivisiGetResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}