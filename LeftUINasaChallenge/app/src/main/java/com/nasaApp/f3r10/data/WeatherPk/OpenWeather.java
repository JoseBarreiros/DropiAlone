package com.nasaApp.f3r10.data.WeatherPk;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nando on 4/10/15.
 */
@Parcel
public class OpenWeather {

    @SerializedName("dt")
    public int dt;
    @SerializedName("id")
    public int  id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public int cod;
    public List<Weather> weather;



    @Override
    public String toString() {
        return name + ", " + id;
    }









}
