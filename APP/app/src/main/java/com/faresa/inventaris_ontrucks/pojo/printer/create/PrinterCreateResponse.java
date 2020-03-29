package com.faresa.inventaris_ontrucks.pojo.printer.create;

import com.google.gson.annotations.SerializedName;

public class PrinterCreateResponse {

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("printerDataCreate")
    private PrinterDataCreate printerDataCreate;

    @SerializedName("message")
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setPrinterDataCreate(PrinterDataCreate printerDataCreate) {
        this.printerDataCreate = printerDataCreate;
    }

    public PrinterDataCreate getPrinterDataCreate() {
        return printerDataCreate;
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
                "PrinterCreateResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",printerDataCreate = '" + printerDataCreate + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}