package com.faresa.inventaris_ontrucks.pojo;

import com.google.gson.annotations.SerializedName;

public class DataAll {

    @SerializedName("jumlah printer")
    private int jumlahPrinter;

    @SerializedName("jumlah pegawai")
    private int jumlahPegawai;

    @SerializedName("jumlah laptop")
    private int jumlahLaptop;

    @SerializedName("jumlah divisi")
    private int jumlahDivisi;

    public void setJumlahPrinter(int jumlahPrinter) {
        this.jumlahPrinter = jumlahPrinter;
    }

    public int getJumlahPrinter() {
        return jumlahPrinter;
    }

    public void setJumlahPegawai(int jumlahPegawai) {
        this.jumlahPegawai = jumlahPegawai;
    }

    public int getJumlahPegawai() {
        return jumlahPegawai;
    }

    public void setJumlahLaptop(int jumlahLaptop) {
        this.jumlahLaptop = jumlahLaptop;
    }

    public int getJumlahLaptop() {
        return jumlahLaptop;
    }

    public void setJumlahDivisi(int jumlahDivisi) {
        this.jumlahDivisi = jumlahDivisi;
    }

    public int getJumlahDivisi() {
        return jumlahDivisi;
    }

    @Override
    public String toString() {
        return
                "DataAll{" +
                        "jumlah printer = '" + jumlahPrinter + '\'' +
                        ",jumlah pegawai = '" + jumlahPegawai + '\'' +
                        ",jumlah laptop = '" + jumlahLaptop + '\'' +
                        ",jumlah divisi = '" + jumlahDivisi + '\'' +
                        "}";
    }
}