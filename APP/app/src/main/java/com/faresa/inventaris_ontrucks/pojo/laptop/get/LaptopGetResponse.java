package com.faresa.inventaris_ontrucks.pojo.laptop.get;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaptopGetResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("data")
    private List<LaptopGetData> data;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setData(List<LaptopGetData> data) {
        this.data = data;
    }

    public List<LaptopGetData> getData() {
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
                "LaptopGetResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}