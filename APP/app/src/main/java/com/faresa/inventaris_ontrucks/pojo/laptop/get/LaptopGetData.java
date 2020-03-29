package com.faresa.inventaris_ontrucks.pojo.laptop.get;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class LaptopGetData implements Parcelable {

    @SerializedName("path")
    private String path;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("pegawai_id")
    private int pegawaiId;

    @SerializedName("file_id")
    private int fileId;

    @SerializedName("operating_system")
    private String operatingSystem;

    @SerializedName("created_at")
    private String createdAt;

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
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
                "LaptopGetData{" +
                        "path = '" + path + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",pegawai_id = '" + pegawaiId + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",operating_system = '" + operatingSystem + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",serial_number = '" + serialNumber + '\'' +
                        ",pIC = '" + pIC + '\'' +
                        ",type = '" + type + '\'' +
                        ",inventaris_code = '" + inventarisCode + '\'' +
                        ",laptop_id = '" + laptopId + '\'' +
                        ",divisi = '" + divisi + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.pegawaiId);
        dest.writeInt(this.fileId);
        dest.writeString(this.operatingSystem);
        dest.writeString(this.createdAt);
        dest.writeString(this.serialNumber);
        dest.writeString(this.pIC);
        dest.writeString(this.type);
        dest.writeString(this.inventarisCode);
        dest.writeInt(this.laptopId);
        dest.writeString(this.divisi);
    }

    public LaptopGetData() {
    }

    protected LaptopGetData(Parcel in) {
        this.path = in.readString();
        this.updatedAt = in.readString();
        this.pegawaiId = in.readInt();
        this.fileId = in.readInt();
        this.operatingSystem = in.readString();
        this.createdAt = in.readString();
        this.serialNumber = in.readString();
        this.pIC = in.readString();
        this.type = in.readString();
        this.inventarisCode = in.readString();
        this.laptopId = in.readInt();
        this.divisi = in.readString();
    }

    public static final Parcelable.Creator<LaptopGetData> CREATOR = new Parcelable.Creator<LaptopGetData>() {
        @Override
        public LaptopGetData createFromParcel(Parcel source) {
            return new LaptopGetData(source);
        }

        @Override
        public LaptopGetData[] newArray(int size) {
            return new LaptopGetData[size];
        }
    };
}