package com.faresa.inventaris_ontrucks.pojo.laptop.delete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaptopDeleteResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("data")
    private List<Data> data;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
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
                "LaptopDeleteResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}