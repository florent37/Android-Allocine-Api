package com.github.florent37.allocineapi.model.Movie;

import com.github.florent37.allocineapi.model.Media;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

import java.io.Serializable;

public class DefaultMedia implements Serializable {

    @Expose
    private Media media;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "DefaultMedia{" +
                "media=" + media +
                '}';
    }
}