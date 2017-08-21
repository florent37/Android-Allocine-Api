package com.github.florent37.allocineapi.model;

import com.github.florent37.allocineapi.model.Movie.Movie;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

/**
 * Created by florentchampigny on 01/05/2014.
 */
public class Subject implements Serializable {

    @Expose
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
