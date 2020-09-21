package com.example.cinema.dto;

public class VoteDTO {

    private String spectator;

    private int vote;

    private String movie;

    public String getSpectator() {
        return spectator;
    }

    public void setSpectator(String spectator) {
        this.spectator = spectator;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
