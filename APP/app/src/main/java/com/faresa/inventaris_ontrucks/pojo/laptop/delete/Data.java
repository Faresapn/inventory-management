package com.faresa.inventaris_ontrucks.pojo.laptop.delete;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("pegawai_id")
    private int pegawaiId;

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

    @SerializedName("laptop_id")
    private int laptopId;

    @SerializedName("divisi")
    private String divisi;

    public void setPegawaiId(int pegawaiId) {
        this.pegawaiId = pegawaiId;
    }

    public int getPegawaiId() {
        return pegawaiId;
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

    public void setLaptopId(int laptopId) {
        this.laptopId = laptopId;
    }

    public int getLaptopId() {
        return laptopId;
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
                "DataCreate{" +
                        "pegawai_id = '" + pegawaiId + '\'' +
                        ",operating_system = '" + operatingSystem + '\'' +
                        ",serial_number = '" + serialNumber + '\'' +
                        ",pIC = '" + pIC + '\'' +
                        ",type = '" + type + '\'' +
                        ",inventaris_code = '" + inventarisCode + '\'' +
                        ",laptop_id = '" + laptopId + '\'' +
                        ",divisi = '" + divisi + '\'' +
                        "}";
    }
}