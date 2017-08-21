package com.github.florent37.allocineapi.model;

import com.github.florent37.allocineapi.model.Theater.Theater;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class Place implements Serializable {

    @Expose
    private Theater theater;

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    @Override
    public String toString() {
        return "Place{" +
                "theater=" + theater +
                '}';
    }
}
