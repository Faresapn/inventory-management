package com.faresa.inventaris_ontrucks.pojo.laptop.create;

import com.google.gson.annotations.SerializedName;

public class LaptopCreateResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("dataCreate")
    private DataCreate dataCreate;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setDataCreate(DataCreate dataCreate) {
        this.dataCreate = dataCreate;
    }

    public DataCreate getDataCreate() {
        return dataCreate;
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
                "LaptopCreateResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",dataCreate = '" + dataCreate + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}