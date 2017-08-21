package com.github.florent37.allocineapi.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class Picture implements Serializable {

    @Expose
    private String path;
    @Expose
    private String href;
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHref(int height) {
        return href+"/r_10000_"+height;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "path='" + path + '\'' +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
