package com.faresa.inventaris_ontrucks.pojo;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("jumlah_pegawai")
    private int jumlahPegawai;

    @SerializedName("jumlah_divisi")
    private int jumlahDivisi;

    @SerializedName("jumlah_laptop")
    private int jumlahLaptop;

    @SerializedName("jumlah_printer")
    private int jumlahPrinter;

    public void setJumlahPegawai(int jumlahPegawai) {
        this.jumlahPegawai = jumlahPegawai;
    }

    public int getJumlahPegawai() {
        return jumlahPegawai;
    }

    public void setJumlahDivisi(int jumlahDivisi) {
        this.jumlahDivisi = jumlahDivisi;
    }

    public int getJumlahDivisi() {
        return jumlahDivisi;
    }

    public void setJumlahLaptop(int jumlahLaptop) {
        this.jumlahLaptop = jumlahLaptop;
    }

    public int getJumlahLaptop() {
        return jumlahLaptop;
    }

    public void setJumlahPrinter(int jumlahPrinter) {
        this.jumlahPrinter = jumlahPrinter;
    }

    public int getJumlahPrinter() {
        return jumlahPrinter;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "jumlah_pegawai = '" + jumlahPegawai + '\'' +
                        ",jumlah_divisi = '" + jumlahDivisi + '\'' +
                        ",jumlah_laptop = '" + jumlahLaptop + '\'' +
                        ",jumlah_printer = '" + jumlahPrinter + '\'' +
                        "}";
    }
}