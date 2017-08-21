package com.github.florent37.allocineapi.model.Movie;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class Poster implements Serializable {

    @Expose
    private String name;
    @Expose
    private String path;
    @Expose
    private String href;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Poster{" +
                "path='" + path + '\'' +
                ",name='" + name + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
