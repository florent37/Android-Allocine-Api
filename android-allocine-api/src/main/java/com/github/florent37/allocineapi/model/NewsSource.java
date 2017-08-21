package com.github.florent37.allocineapi.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class NewsSource implements Serializable {

    @Expose
    private Integer code;
    @Expose
    private String name;
    @Expose
    private String href;

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "NewsSource{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
