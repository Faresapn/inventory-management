package com.faresa.inventaris_ontrucks.pojo.printer.update;

import com.google.gson.annotations.SerializedName;

public class DataPrinterUpdate {

    @SerializedName("path")
    private String path;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("file_id")
    private int fileId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("type")
    private String type;

    @SerializedName("printer_id")
    private int printerId;

    @SerializedName("divisi")
    private String divisi;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setPrinterId(int printerId) {
        this.printerId = printerId;
    }

    public int getPrinterId() {
        return printerId;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getDivisi() {
        return divisi;
    }

    @Override
    public String toString() {
        return
                "DataPrinterUpdate{" +
                        "path = '" + path + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",type = '" + type + '\'' +
                        ",printer_id = '" + printerId + '\'' +
                        ",divisi = '" + divisi + '\'' +
                        "}";
    }
}