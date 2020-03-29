package com.faresa.inventaris_ontrucks.pojo.divisi.get;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataGet implements Parcelable {

    @SerializedName("divisi_id")
    private int divisiId;

    @SerializedName("name")
    private String name;

    public void setDivisiId(int divisiId) {
        this.divisiId = divisiId;
    }

    public int getDivisiId() {
        return divisiId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return
                "DataGet{" +
                        "divisi_id = '" + divisiId + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.divisiId);
        dest.writeString(this.name);
    }

    public DataGet() {
    }

    protected DataGet(Parcel in) {
        this.divisiId = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<DataGet> CREATOR = new Parcelable.Creator<DataGet>() {
        @Override
        public DataGet createFromParcel(Parcel source) {
            return new DataGet(source);
        }

        @Override
        public DataGet[] newArray(int size) {
            return new DataGet[size];
        }
    };
}