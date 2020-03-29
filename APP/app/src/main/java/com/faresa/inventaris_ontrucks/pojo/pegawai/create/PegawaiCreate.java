package com.faresa.inventaris_ontrucks.pojo.pegawai.create;

public class PegawaiCreate {
    private int divisiId;
    private int pegawaiId;
    private int fileId;
    private String name;
    private String divisiName;

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

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return fileId;
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
                "PegawaiCreate{" +
                        "divisi_id = '" + divisiId + '\'' +
                        ",pegawai_id = '" + pegawaiId + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",name = '" + name + '\'' +
                        ",divisi_name = '" + divisiName + '\'' +
                        "}";
    }
}
