package com.faresa.inventaris_ontrucks.pojo.printer.create;

import com.google.gson.annotations.SerializedName;

public class PrinterDataCreate {

    @SerializedName("path")
    private String path;

    @SerializedName("file_id")
    private int fileId;

    @SerializedName("printer_id")
    private int printerId;

    @SerializedName("type")
    private String type;

    @SerializedName("inventaris_code")
    private String inventarisCode;

    @SerializedName("divisi")
    private String divisi;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setPrinterId(int printerId) {
        this.printerId = printerId;
    }

    public int getPrinterId() {
        return printerId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setInventarisCode(String inventarisCode) {
        this.inventarisCode = inventarisCode;
    }

    public String getInventarisCode() {
        return inventarisCode;
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
                "PrinterDataCreate{" +
                        "path = '" + path + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",printer_id = '" + printerId + '\'' +
                        ",type = '" + type + '\'' +
                        ",inventaris_code = '" + inventarisCode + '\'' +
                        ",divisi = '" + divisi + '\'' +
                        "}";
    }
}