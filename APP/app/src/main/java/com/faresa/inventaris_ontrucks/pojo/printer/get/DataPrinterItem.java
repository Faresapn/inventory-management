package com.faresa.inventaris_ontrucks.pojo.printer.get;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataPrinterItem implements Parcelable {

    @SerializedName("path")
    private String path;

    @SerializedName("file_id")
    private int fileId;

    @SerializedName("printer_id")
    private int printerId;

    @SerializedName("type")
    private String type;

    @SerializedName("inventaris_code")
    private String inventarisCode;

    @SerializedName("divisi")
    private String divisi;

    protected DataPrinterItem(Parcel in) {
        this.path = in.readString();
        this.fileId = in.readInt();
        this.printerId = in.readInt();
        this.type = in.readString();
        this.inventarisCode = in.readString();
        this.divisi = in.readString();
    }

    public static final Creator<DataPrinterItem> CREATOR = new Creator<DataPrinterItem>() {
        @Override
        public DataPrinterItem createFromParcel(Parcel in) {
            return new DataPrinterItem(in);
        }

        @Override
        public DataPrinterItem[] newArray(int size) {
            return new DataPrinterItem[size];
        }
    };

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return fileId;
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

    @Override
    public String toString() {
        return
                "DataPrinterItem{" +
                        "path = '" + path + '\'' +
                        ",file_id = '" + fileId + '\'' +
                        ",printer_id = '" + printerId + '\'' +
                        ",type = '" + type + '\'' +
                        ",inventaris_code = '" + inventarisCode + '\'' +
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
        dest.writeInt(this.fileId);
        dest.writeInt(this.printerId);
        dest.writeString(this.type);
        dest.writeString(this.inventarisCode);
        dest.writeString(this.divisi);
    }
}