package com.faresa.inventaris_ontrucks.pojo.pegawai.delete;

public class PegawaiDeleteResponse {
    private String statusCode;
    private String message;

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
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
                "PegawaiDeleteResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
