package com.faresa.inventaris_ontrucks.ui.pegawai;

import android.os.Parcel;
import android.os.Parcelable;

public class Spin implements Parcelable {
    private int id;
    private String name;


    public Spin(int id, String name) {
        this.id = id;
        this.name = name;

    }

    protected Spin(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Spin> CREATOR = new Creator<Spin>() {
        @Override
        public Spin createFromParcel(Parcel in) {
            return new Spin(in);
        }

        @Override
        public Spin[] newArray(int size) {
            return new Spin[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return name;


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
