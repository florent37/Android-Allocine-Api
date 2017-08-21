package com.github.florent37.allocineapi.model;

import com.github.florent37.allocineapi.model.Movie.Movie;
import com.github.florent37.allocineapi.model.Person.PersonSmall;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class AllocineResponseSmall implements Serializable {

    @Expose
    private FeedSmall feed;

    @Expose
    private Movie movie;

    @Expose
    private PersonSmall person;

    public PersonSmall getPerson() {
        return person;
    }

    public void setPerson(PersonSmall person) {
        this.person = person;
    }

    public FeedSmall getFeed() {
        return feed;
    }

    public void setFeed(FeedSmall feed) {
        this.feed = feed;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Field{" +
                "feed=" + feed +
                ", movie=" + movie +
                ", person=" + person +
                '}';
    }
}
