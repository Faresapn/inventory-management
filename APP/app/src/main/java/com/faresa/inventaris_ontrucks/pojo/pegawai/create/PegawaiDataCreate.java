package com.faresa.inventaris_ontrucks.pojo.pegawai.create;

import com.google.gson.annotations.SerializedName;

public class PegawaiDataCreate {

    @SerializedName("divisi_id")
    private String divisiId;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("pegawai_id")
    private int pegawaiId;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    public void setDivisiId(String divisiId) {
        this.divisiId = divisiId;
    }

    public String getDivisiId() {
        return divisiId;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return
                "PegawaiDataCreate{" +
                        "divisi_id = '" + divisiId + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",pegawai_id = '" + pegawaiId + '\'' +
                        ",name = '" + name + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        "}";
    }
}