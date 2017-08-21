package com.github.florent37.allocineapi.model.Person;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class CastingShort implements Serializable {

    @Expose
    private String directors;
    @Expose
    private String actors;

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "CastingShort{" +
                "directors='" + directors + '\'' +
                ", actors='" + actors + '\'' +
                '}';
    }
}
