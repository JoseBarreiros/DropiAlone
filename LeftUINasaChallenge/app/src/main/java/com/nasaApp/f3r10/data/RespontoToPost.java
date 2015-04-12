package com.nasaApp.f3r10.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by nando on 4/10/15.
 */
@Parcel
public class RespontoToPost {

    @SerializedName("status_code")
    public int status_code;

    @Override
    public String toString() {
        return "El status es" + ", " + status_code;
    }


}
