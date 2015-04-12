package com.nasaApp.f3r10.data.WeatherPk;

import org.parceler.Parcel;

/**
 * Created by nando on 4/10/15.
 */
@Parcel
public class Weather {

    public int id;
    public String main;
    public String description;
    public String icon;

    @Override
    public String toString() {
        return main + ", " + description;
    }


}
