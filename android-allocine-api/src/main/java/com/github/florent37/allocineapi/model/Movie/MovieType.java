package com.github.florent37.allocineapi.model.Movie;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class MovieType implements Serializable {

    @Expose
    private DefaultMedia defaultMedia;

    public DefaultMedia getDefaultMedia() {
        return defaultMedia;
    }

    public void setDefaultMedia(DefaultMedia defaultMedia) {
        this.defaultMedia = defaultMedia;
    }

    @Override
    public String toString() {
        return "MovieType{" +
                "defaultMedia=" + defaultMedia +
                '}';
    }
}