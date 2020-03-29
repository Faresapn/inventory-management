package com.faresa.inventaris_ontrucks.pojo.pegawai.update;

import com.google.gson.annotations.SerializedName;

public class PegawaiDataUpdate {

    @SerializedName("path")
    private String path;

    @SerializedName("divisi_id")
    private int divisiId;

    @SerializedName("pegawai_id")
    private int pegawaiId;

    @SerializedName("name")
    private String name;

    @SerializedName("divisi_name")
    private String divisiName;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setDivisiId(int divisiId) {
        this.divisiId = divisiId;
    }

    public int getDivisiId() {
        return divisiId;
    }

    public void setPegawaiId(int pegawaiId) {
        this.pegawaiId = pegawaiId;
    }

    public int getPegawaiId() {
        return pegawaiId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDivisiName(String divisiName) {
        this.divisiName = divisiName;
    }

    public String getDivisiName() {
        return divisiName;
    }

    @Override
    public String toString() {
        return
                "PegawaiDataUpdate{" +
                        "path = '" + path + '\'' +
                        ",divisi_id = '" + divisiId + '\'' +
                        ",pegawai_id = '" + pegawaiId + '\'' +
                        ",name = '" + name + '\'' +
                        ",divisi_name = '" + divisiName + '\'' +
                        "}";
    }
}