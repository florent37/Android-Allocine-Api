package com.github.florent37.allocineapi.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

/**
 * Created by florentchampigny on 01/05/2014.
 */
public class Thumbnail implements Serializable {
    @Expose
    private String href;

    public String getHref(int height) {
        return href+"/r_10000_"+height;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "href='" + href + '\'' +
                '}';
    }
}
