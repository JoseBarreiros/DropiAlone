package com.nasaApp.f3r10.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by nando on 4/10/15.
 */
@Parcel
public class NameList {
    @SerializedName("name_pest")
    public String name_pest;
    @SerializedName("media_pest")
    public String media_pest;

    @Override
    public String toString() {
        return name_pest + ", " + media_pest;
    }
}
