package com.github.florent37.allocineapi.model.Movie;


import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

import java.io.Serializable;


public class Distributor implements Serializable {

    @Expose
    private Integer code;
    @Expose
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
