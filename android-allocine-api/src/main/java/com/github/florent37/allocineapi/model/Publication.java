package com.github.florent37.allocineapi.model;


import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;


public class Publication implements Serializable {

    @Expose
    private String dateStart;

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "dateStart='" + dateStart + '\'' +
                '}';
    }
}
