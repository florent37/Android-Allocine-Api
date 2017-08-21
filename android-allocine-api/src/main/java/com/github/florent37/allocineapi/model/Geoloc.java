package com.github.florent37.allocineapi.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class Geoloc implements Serializable {

    @Expose
    private Double lat;
    @SerializedName("long")
    @Expose
    private Double _long;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    @Override
    public String toString() {
        return "Geoloc{" +
                "lat=" + lat +
                ", _long=" + _long +
                '}';
    }
}
