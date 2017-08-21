package com.github.florent37.allocineapi.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class Writer implements Serializable {

    @Expose
    private String code;
    @Expose
    private String name;
    @Expose
    private String avatar;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
