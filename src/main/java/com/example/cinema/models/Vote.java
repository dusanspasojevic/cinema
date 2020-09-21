package com.example.cinema.models;


import javax.persistence.*;

@Entity(name = "votes")
public class Vote {

    @Id
    private int id;

    private int vote;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spectator_id")
    private User spectator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public User getSpectator() {
        return spectator;
    }

    public void setSpectator(User spectator) {
        this.spectator = spectator;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Vote(){};

    public Vote(int id, int vote, User spectator, Movie movie) {
        this.id = id;
        this.vote = vote;
        this.spectator = spectator;
        this.movie = movie;
    }
}
