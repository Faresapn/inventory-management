package com.faresa.inventaris_ontrucks.pojo.pegawai.create;

public class PegawaiCreateResponse {
    private String statusCode;
    private PegawaiCreate pegawaiCreate;
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setPegawaiCreate(PegawaiCreate pegawaiCreate) {
        this.pegawaiCreate = pegawaiCreate;
    }

    public PegawaiCreate getPegawaiCreate() {
        return pegawaiCreate;
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
                "PegawaiCreateResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",pegawaiCreate = '" + pegawaiCreate + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
