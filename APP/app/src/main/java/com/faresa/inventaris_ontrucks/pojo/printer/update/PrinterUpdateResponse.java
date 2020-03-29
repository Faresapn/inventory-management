package com.faresa.inventaris_ontrucks.pojo.printer.update;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PrinterUpdateResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("data")
    private List<PrinterDataUpdate> data;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setData(List<PrinterDataUpdate> data) {
        this.data = data;
    }

    public List<PrinterDataUpdate> getData() {
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
                "PrinterUpdateResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}