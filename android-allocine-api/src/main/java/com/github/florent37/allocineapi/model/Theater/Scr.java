package com.github.florent37.allocineapi.model.Theater;

import com.github.florent37.allocineapi.model.ModelObject;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Scr implements Serializable {

    @Expose
    private String d;
    @Expose
    private List<ModelObject> t = new ArrayList<ModelObject>();

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public List<ModelObject> getT() {
        return t;
    }

    public void setT(List<ModelObject> t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "Scr{" +
                "d='" + d + '\'' +
                ", t=" + t +
                '}';
    }
}
