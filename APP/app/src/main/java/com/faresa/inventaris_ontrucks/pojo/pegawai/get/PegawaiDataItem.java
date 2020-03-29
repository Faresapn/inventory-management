package com.faresa.inventaris_ontrucks.pojo.pegawai.get;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PegawaiDataItem implements Parcelable {

    @SerializedName("path")
    private String path;

    @SerializedName("divisi_id")
    private int divisiId;

    @SerializedName("pegawai_id")
    private int pegawaiId;

    @SerializedName("file_id")
    private int fileId;

    @SerializedName("name")
    private String name;

    @SerializedName("divisi_name")
    private String divisiName;

    protected PegawaiDataItem(Parcel in) {
        this.path = in.readString();
        this.divisiId = in.readInt();
        this.pegawaiId = in.readInt();
        this.fileId = in.readInt();
        this.name = in.readString();
        this.divisiName = in.readString();
    }

    public static final Creator<PegawaiDataItem> CREATOR = new Creator<PegawaiDataItem>() {
        @Override
        public PegawaiDataItem createFromParcel(Parcel in) {
            return new PegawaiDataItem(in);
        }

        @Override
        public PegawaiDataItem[] newArray(int size) {
            return new PegawaiDataItem[size];
        }
    };

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
                "PegawaiDataItem{" +
                        "path = '" + path + '\'' +
                        ",divisi_id = '" + divisiId + '\'' +
                        ",pegawai_id = '" + pegawaiId + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",name = '" + name + '\'' +
                        ",divisi_name = '" + divisiName + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeInt(this.divisiId);
        dest.writeInt(this.pegawaiId);
        dest.writeInt(this.fileId);
        dest.writeString(this.name);
        dest.writeString(this.divisiName);
    }
}