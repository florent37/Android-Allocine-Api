package com.github.florent37.allocineapi.model;

import com.github.florent37.allocineapi.model.Movie.Movie;
import com.github.florent37.allocineapi.model.Person.PersonFull;
import com.github.florent37.allocineapi.model.Theater.Theater;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

import java.io.Serializable;

public class AllocineResponse implements Serializable {

    @Expose
    private Feed feed;

    @Expose
    private Movie movie;

    @Expose
    private Theater theater;

    @Expose
    private PersonFull person;

    public PersonFull getPerson() {
        return person;
    }

    public void setPerson(PersonFull person) {
        this.person = person;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    @Override
    public String toString() {
        return "Field{" +
                "feed=" + feed +
                ", movie=" + movie +
                ", person=" + person +
                ", theater=" + theater +
                '}';
    }
}
