package com.github.florent37.allocineapi.model;

import com.github.florent37.allocineapi.model.Movie.Movie;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class Participation implements Serializable {

    @Expose
    private Movie movie;
    @Expose
    private ModelObject activity;
    @Expose
    private Tvseries tvseries;
    @Expose
    private String role;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ModelObject getActivity() {
        return activity;
    }

    public void setActivity(ModelObject activity) {
        this.activity = activity;
    }

    public Tvseries getTvseries() {
        return tvseries;
    }

    public void setTvseries(Tvseries tvseries) {
        this.tvseries = tvseries;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "movie=" + movie +
                ", activity=" + activity +
                ", tvseries=" + tvseries +
                ", role='" + role + '\'' +
                '}';
    }
}
