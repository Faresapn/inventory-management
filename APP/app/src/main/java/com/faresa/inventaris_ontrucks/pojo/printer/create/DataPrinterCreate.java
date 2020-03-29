package com.faresa.inventaris_ontrucks.pojo.printer.create;

public class DataPrinterCreate {
    private String updatedAt;
    private int fileId;
    private String createdAt;
    private int printerId;
    private String type;
    private String divisi;

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

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getDivisi() {
        return divisi;
    }

    @Override
    public String toString() {
        return
                "DataPrinterCreate{" +
                        "updated_at = '" + updatedAt + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",printer_id = '" + printerId + '\'' +
                        ",type = '" + type + '\'' +
                        ",divisi = '" + divisi + '\'' +
                        "}";
    }
}
