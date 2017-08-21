package com.github.florent37.allocineapi.model.Theater;

import com.github.florent37.allocineapi.model.Movie.Movie;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class OnShow implements Serializable {

    @Expose
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "OnShow{" +
                "movie=" + movie +
                '}';
    }
}
