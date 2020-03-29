package com.faresa.inventaris_ontrucks.pojo.laptop.create;

import com.google.gson.annotations.SerializedName;

public class DataCreate {

    @SerializedName("pegawai_id")
    private String pegawaiId;

    @SerializedName("file_id")
    private int fileId;

    @SerializedName("operating_system")
    private String operatingSystem;

    @SerializedName("serial_number")
    private String serialNumber;

    @SerializedName("PIC")
    private String pIC;

    @SerializedName("type")
    private String type;

    @SerializedName("inventaris_code")
    private String inventarisCode;

    @SerializedName("divisi")
    private String divisi;

    @SerializedName("laptop_id")
    private int laptopId;

    public void setPegawaiId(String pegawaiId) {
        this.pegawaiId = pegawaiId;
    }

    public String getPegawaiId() {
        return pegawaiId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setPIC(String pIC) {
        this.pIC = pIC;
    }

    public String getPIC() {
        return pIC;
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

    public void setLaptopId(int laptopId) {
        this.laptopId = laptopId;
    }

    public int getLaptopId() {
        return laptopId;
    }

    @Override
    public String toString() {
        return
                "DataCreate{" +
                        "pegawai_id = '" + pegawaiId + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",operating_system = '" + operatingSystem + '\'' +
                        ",serial_number = '" + serialNumber + '\'' +
                        ",pIC = '" + pIC + '\'' +
                        ",type = '" + type + '\'' +
                        ",inventaris_code = '" + inventarisCode + '\'' +
                        ",divisi = '" + divisi + '\'' +
                        ",laptop_id = '" + laptopId + '\'' +
                        "}";
    }
}